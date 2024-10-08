package core.util;

import core.model.HOVerwaltung;

/**
 * Utility class for various String related operations.
 * 
 */
public class StringUtils {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private StringUtils() {
	}

	/**
	 * Checks if a <code>String</code> contains digits only. This means that for
	 * every character x in the given string <code>Character.isDigit(x)</code>
	 * has to be <code>true</code>.
	 * 
	 * @param str
	 *            the string to check.
	 * @return <code>true</code> if the given string contains digits only,
	 *         <code>false</code> if the given string is <code>null</code>,
	 *         empty or contains at least one character which is not a digit.
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a given <code>String</code> is empty. This method is
	 * <code>null</code>-safe which means that it will return <code>true</code>
	 * if the given parameter is <code>null</code>.
	 * 
	 * @param str
	 *            the string to check.
	 * @return <code>true</code> if the given string is empty or
	 *         <code>null</code>, <code>false</code> otherwise.
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.isEmpty());
	}

	private static String _separator = null;
	private static String getSeparator(){
		if (_separator==null ) {
			_separator = " " + HOVerwaltung.instance().getLanguageString("ls.match.result.separation") + " ";
		}
		return _separator;
	}

	public static String getResultString(int homeGoals, int awayGoals, String resultExtensionAbbreviation) {
		if (homeGoals < 0 || awayGoals < 0)
			return "  "+ getSeparator();

		final StringBuilder buffer = new StringBuilder();
		if (homeGoals < 10) {
			buffer.append(" ");
		}
		buffer.append(homeGoals);
		buffer.append(getSeparator());
		buffer.append(awayGoals);
		if(! resultExtensionAbbreviation.isEmpty()){
			buffer.append(" ").append(resultExtensionAbbreviation);
		}
		return buffer.toString();
	}

	public static String capitalizeWord(String str){
		String[] words =str.split("\\s");
		StringBuilder capitalizeWord= new StringBuilder();
		for(String w:words){
			String first=w.substring(0,1);
			String afterfirst=w.substring(1);
			capitalizeWord.append(first.toUpperCase()).append(afterfirst).append(" ");
		}
		return capitalizeWord.toString().trim();
	}

}
