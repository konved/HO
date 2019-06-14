package core.model;

import core.datatype.CBItem;
import core.db.DBManager;
import core.file.ExampleFileFilter;
import core.gui.HOMainFrame;
import core.gui.RefreshManager;
import core.net.login.LoginWaitDialog;
import core.training.TrainingManager;
import core.util.HOLogger;
import core.util.UTF8Control;
import module.lineup.Lineup;
import core.HO;

import java.lang.Integer;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @author tom
 */
public class HOVerwaltung {
	// ~ Static fields/initializers
	// -----------------------------------------------------------------

	/** singelton */
	protected static HOVerwaltung m_clInstance;

	// ~ Instance fields
	// ----------------------------------------------------------------------------

	/** das Model */
	protected HOModel m_clHoModel;

	/** Resource */
	protected ResourceBundle languageBundle;

	public int getId() {
		return id;
	}

	private int id;

	// ---- plurals ----

	// separator is semicolon sign
	private static final String PluralFormsSeparator = ";";

	private int PluralRuleId;
	protected int getPluralRuleId() {
		return PluralRuleId;
	}

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new HOVerwaltung object.
	 */
	private HOVerwaltung() {
	}

	// ----------------- Auxiliary methods -----------------

	/**
	 * Get the HOVerwaltung singleton instance.
	 */
	public static HOVerwaltung instance() {
		if (m_clInstance == null) {
			m_clInstance = new HOVerwaltung();

			DBManager.instance().getFaktorenFromDB();

			// Krücke bisher
			// berechnung.FormulaFactors.instance ().init ();
		}

		return m_clInstance;
	}

	public HOModel getModel() {
		return m_clHoModel;
	}

	/**
	 * Set the HOModel.
	 */
	public void setModel(HOModel model) {
		m_clHoModel = model;
	}

	/**
	 * interne Func die ein Model aus der DB lädt
	 */
	protected HOModel loadModel(int id) {
		final HOModel model = new HOModel();
		model.setSpieler(DBManager.instance().getSpieler(id));
		model.setAllOldSpieler(DBManager.instance().getAllSpieler());
		model.setAufstellung(DBManager.instance().getAufstellung(id, Lineup.DEFAULT_NAME));
		model.setLastAufstellung(DBManager.instance().getAufstellung(id, Lineup.DEFAULT_NAMELAST));
		model.setBasics(DBManager.instance().getBasics(id));
		model.setFinanzen(DBManager.instance().getFinanzen(id));
		model.setLiga(DBManager.instance().getLiga(id));
		model.setStadium(DBManager.instance().getStadion(id));
		model.setTeam(DBManager.instance().getTeam(id));
		model.setVerein(DBManager.instance().getVerein(id));
		model.setID(id);
		model.setSpielplan(DBManager.instance().getSpielplan(-1, -1));
		model.setXtraDaten(DBManager.instance().getXtraDaten(id));
		model.setStaff(DBManager.instance().getStaffByHrfId(id));
		
		return model;
	}

	/**
	 * ersetzt das aktuelle model durch das aus der DB mit der angegebenen ID
	 */
	public void loadHoModel(int id) {
		m_clHoModel = loadModel(id);
	}

	/**
	 * läadt das zuletzt importtiert model ein
	 */
	public void loadLatestHoModel() {
		int id = DBManager.instance().getLatestHrfId();
		this.id = id;
		m_clHoModel = loadModel(id);
	}

	/**
	 * Recalculate subskills since a certain HRF date. If the HRF date is null,
	 * the whole training history is recalculated.
	 */
	public void recalcSubskills(boolean showWait, Timestamp hrfDate) {
		HOLogger.instance().log(getClass(), "Start full subskill calculation. " + new Date());
		long start = System.currentTimeMillis();
		if (hrfDate == null) {
			hrfDate = new Timestamp(0);
		}

		LoginWaitDialog waitDialog = null;

		if (showWait) {
			waitDialog = new LoginWaitDialog(HOMainFrame.instance(), false);
			waitDialog.setVisible(true);
		}

		// Make sure the training week list is up to date.
		TrainingManager.instance().refreshTrainingWeeks();
		
		final Vector<CBItem> hrfListe = new Vector<CBItem>();
		hrfListe.addAll(DBManager.instance().getCBItemHRFListe(hrfDate));
		Collections.reverse(hrfListe);
		long s1, s2, lSum = 0, mSum = 0;
		HOLogger.instance().log(getClass(), "Subskill calculation prepared. " + new Date());
		for (int i = 0; i < hrfListe.size(); i++) {
			try {
				if (showWait && waitDialog != null) {
					waitDialog.setValue((int) ((i * 100d) / hrfListe.size()));
				}
				s1 = System.currentTimeMillis();
				final HOModel model = this.loadModel((hrfListe.get(i)).getId());
				lSum += (System.currentTimeMillis() - s1);
				s2 = System.currentTimeMillis();
				model.calcSubskills();
				mSum += (System.currentTimeMillis() - s2);
			} catch (Exception e) {
				HOLogger.instance().log(getClass(), "recalcSubskills : ");
				HOLogger.instance().log(getClass(), e);
			}
		}

		if (showWait && waitDialog != null) {
			waitDialog.setVisible(false);
		}

		// Erneut laden, da sich die Subskills geändert haben
		loadLatestHoModel();

		RefreshManager.instance().doReInit();
		HOLogger.instance().log(
				getClass(),
				"Subskill calculation done. " + new Date() + " - took "
						+ (System.currentTimeMillis() - start) + "ms ("
						+ (System.currentTimeMillis() - start) / 1000L + " sec), lSum=" + lSum
						+ ", mSum=" + mSum);
	}

	// ----------------- Translation bundles -----------------
	
	public ResourceBundle getResource() {
		return languageBundle;
	}

	public void setResource(String pfad) {
		try {
			 languageBundle = ResourceBundle.getBundle("sprache." + pfad, new UTF8Control());
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), e);
		}
		try {
			PluralRuleId = Integer.parseInt(instance().getLanguageString("core.PluralRuleId"));
		} catch (Exception e) {
			HOLogger.instance().warning(getClass(),
					new String("Cannot determine pluralization rule index.\n" +
							"Please make sure that string \"core.PluralRuleId\" exists " +
							"in " + pfad + " resource and contains suited index."));
			PluralRuleId = 0; // no rule - no plurals
		}
	}

	/**
	 * Checked die Sprachdatei oder Fragt nach einer passenden
	 */
	public static void checkLanguageFile(String dateiname) {
		try {
            final java.io.InputStream sprachdatei = HOVerwaltung.class.getClassLoader().getResourceAsStream("sprache/" + dateiname
					+ ".properties");

			if (sprachdatei != null) {
				double sprachfileversion = 0;
				ResourceBundle temp = ResourceBundle.getBundle("sprache." + dateiname, new UTF8Control());

				try {
					sprachfileversion = Double.parseDouble(temp.getString("Version"));
				} catch (Exception e) {
					HOLogger.instance().log(HOMainFrame.class, "not use " + dateiname);
				}

//				if (sprachfileversion >= HO.SPRACHVERSION) {
//					HOLogger.instance().log(HOMainFrame.class, "use " + dateiname);
//
//					// ok!!
//					return;
//				}

				HOLogger.instance().log(HOMainFrame.class, "use " + dateiname);
				// ok!!
				return;

				//HOLogger.instance().log(HOMainFrame.class, "not use " + dateiname);

			}
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, "not use " + e);
		}

		// Irgendein Fehler -> neue Datei aussuchen!
		// new gui.menue.optionen.InitOptionsDialog();
		UserParameter.instance().sprachDatei = "English";
	}

	public static String[] getLanguageFileNames() {
		String[] files = null;
		final Vector<String> sprachdateien = new Vector<String>();

		try {
			// java.net.URL resource = new
			// gui.vorlagen.ImagePanel().getClass().getClassLoader().getResource(
			// "sprache" );

//            java.net.URL url = HOVerwaltung.class.getClassLoader().getResource("sprache");
//            java.net.JarURLConnection connection = (java.net.JarURLConnection) url.openConnection();
//            String filepath = (String)connection.getJarFileURL().toURI();

            java.io.InputStream is = HOVerwaltung.class.getClassLoader().getResourceAsStream("sprache/ListLanguages.txt");
            java.util.Scanner s = new java.util.Scanner(is);
            java.util.ArrayList<String> llist = new java.util.ArrayList<String>();
            while (s.hasNext()){
                llist.add(s.next());
            }
            s.close();

            files = llist.toArray(new String[llist.size()]);

		} catch (Exception e) {
			HOLogger.instance().log(HOVerwaltung.class, e);
		}

		return files;
	}

	// ----------------- Translations -----------------

	/**
	 * Returns string part index to use for given number in the selected locale.
	 * Index numeration from
	 *    https://developer.mozilla.org/docs/Mozilla/Localization/Localization_and_Plurals
	 * Thanks to http://mlocati.github.io/cldr-to-gettext-plural-rules for reference.
	 *
	 * @param n
	 *            number n determines the plural form to use
	 * @return string index to use for given number.
	 */
	protected int getPluralIndex(int n) {
		int id;

		// Check for correct init (PluralRuleId MUST be set)
		if (instance().getResource() == null) {
			// Still not fully initialized. Trying to reinit.
			setResource(UserParameter.instance().sprachDatei);
			// We should never reach this code block
			HOLogger.instance().log(getClass(), new String("getPluralIndex(): "+
					"uninitialized HOVerwaltung instance.\n"+
					"\tCalled with PluralRuleId="+getPluralRuleId()+" and PluralIndex="+n));
		}

		switch (getPluralRuleId()) {
			case 1: // Germanic, Finno-Ugric, some Romanic, Greek, Bulgarian, ...
				// Two forms, singular for one only
				id = n==1 ? 0 : 1;
				break;
			case 2:  // French, pt_BR
				// Two forms, singular for zero and one
				id = n<=1 ? 0 : 1;
				break;
			case 3: // Latvian
				// Three forms: ends in 0 and 11~19;ends in 1 excl 11;others
				id = n%10==0 || (n%100>=11 && n%100<=19) ? 0 : n%10==1 && n%100 != 11 ? 1 : 2;
				break;
			case 4: // Scottish Gaelic
				// Four forms: 1,11;2,12;3~10,13~19;others
				id = n==1 || n==11 ? 0 : n==2 || n==12 ? 1 : (n>=3 && n<=10) || (n>=13 && n<=19) ? 2 : 3;
				break;
			case 5: // Romanian
				// Three forms: 1;0,ends in 01~19;others
				id = n==1 ? 0 : n==0 || (n%100>=1 && n%100<=19) ? 1 : 2;
				break;
			case 6: // Lithuanian
				// Three forms: ends in 1 excl 11;ends in 2~9 excl 12~19;others
				id = n%10==1 && n%100!=11 ? 0 : n%10>=2 && n%10<=9 && (n%100<11 || n%100>19) ? 1 : 2;
				break;
			case 7: // Belarusian, Russian, Ukrainian
			case 19: // Bosnian, Croatian, Serbian - their rules differ from East Slavic only in unused case
				// Three forms: ends in 1 excl 11;ends in 2~4 excl 12~14;others
				id = n%10==1 && n%100!=11 ? 0 : n%10>=2 && n%10<=4 && (n%100<12 || n%100>14) ? 1 : 2;
				break;
			case 8: // Czech, Slovak
				// Three forms: 1;is 2~4;others
				id = n==1 ? 0 : n>=2 && n<=4 ? 1 : 2;
				break;
			case 9: // Polish
				// Three forms: 1;ends in 2~4 excl 12~14;others
				id = n==1 ? 0 : n%10>=2 && n%10<=4 && (n%100<12 || n%100>14) ? 1 : 2;
				break;
			case 10: // Slovenian
				// Four forms: ends in 01;in 02;in 03~04;others
				id = n%100==1 ? 0 : n%100==2 ? 1 : n%100==3 || n%100==4 ? 2 : 3;
				break;
			case 11: // Irish Gaelic
				// Five forms: 1;2;3~6;7~10;others
				id = n==1 ? 0 : n==2 ? 1 : n>=3 && n<=6 ? 2 : n>=7 && n<=10 ? 3 : 4;
				break;
			case 12: // Arabic
				// Six forms: 1;2;ends in 03~10;others;ends in 00~02 excl 0~2;0
				// (NB: We are using Mozilla's indices for the Arabic, not CLDR's ones)
				id = n==1 ? 0 : n==2 ? 1 : n%100>=3 && n%100<=10 ? 2 :
					n>=100 && n%100<=2 ? 4 : n==0 ? 5 : 3;
				break;
			case 13: // Maltese
				// Four forms: 1;0,ends in 02~10;ends in 11~19;others
				id = n==1 ? 0 : n==0 || (n%100>=2 && n%100<=10) ? 1 : n%100>=11 && n%100<=19 ? 2 : 3;
				break;
			case 15: // Icelandic, Macedonian
				// Two forms: ends in 1 excl 11;others
				id = n%10==1 || n%100!=11 ? 0 : 1;
				break;
			case 16: // Breton
				// Five forms:
				// ends in 1 excl 11,71,91;ends in 2 excl 12,72,92;
				// ends in 3,4,9 excl 1x,7x,9x;ends in 1000000;others
				id = n%10==1 && n%100!=11 && n%100!=71 && n%100!=91 ? 0 :
					n%10==2 && n%100!=12 && n%100!=72 && n%100!=92 ? 1 :
					(n%10==3 || n%10==4 || n%10==9) && (n%100<13 || n%100>19) &&
						(n%100<73 || n%100>79) && n%100<93 ? 2 :
					n!=0 && n%1000000==0 ? 3 : 4;
				break;
			case 17: // Shuar
				// Two forms: zero and others
				id = n==0 ? 0 : 1;
				break;
			case 18: // Welsh
				// Six forms: 0;1;2;3;6;others
				id = n<=3 ? n : n==6 ? 4 : 5;
				break;
			case 0: // most East Asian incl CJK, Persian, Turkish, ...
			case 14: // unused
			default: // no information
				// no plurals, only one form
				id = 0;
				break;
		}
		return id;
	}

	// ---- Methods to get translated string ----

	/**
	 * Returns the String connected to the active language file or connected to
	 * the english language file. Returns !key! if the key can not be found.
	 * 
	 * @param key
	 *            Key to be searched in language files
	 * 
	 * @return String connected to the key or !key! if nothing can be found in
	 *         language files
	 */
	public String getLanguageString(String key) {
		String temp = null;
		try {
			temp = languageBundle.getString(key);
		} catch (Exception e) {
			// Do nothing, it just throws error if key is missing. 
		}
			if (temp != null)
			return temp;
		// Search in english.properties if nothing found and active language not
		// english
		if (!core.model.UserParameter.instance().sprachDatei.equalsIgnoreCase("english")) {
			
			ResourceBundle tempBundle = ResourceBundle.getBundle("sprache.English", new UTF8Control());

			try {
				temp = tempBundle.getString(key);
			} catch (Exception e) {
				// Ignore
			}
			
			if (temp != null)
				return temp;
		}

		HOLogger.instance().warning(getClass(), "getLanguageString: '" + key + "' not found!");
		return "!" + key + "!";
	}

	/**
	 * Gets a parameterized message for the current language.
	 * 
	 * @param key
	 *            the key for the message in the language file.
	 * @param values
	 *            the values for the message
	 * @return the message for the specified key where the placeholders are
	 *         replaced by the given value(s).
	 */
	public String getLanguageString(String key, Object... values) {
		String str = getLanguageString(key);
		return MessageFormat.format(str, values);
	}
}
