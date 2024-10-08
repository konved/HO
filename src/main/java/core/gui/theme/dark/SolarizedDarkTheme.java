package core.gui.theme.dark;

import com.github.weisj.darklaf.DarkLaf;
import com.github.weisj.darklaf.LafManager;
import core.gui.theme.HOColorName;
import core.gui.theme.ThemeManager;
import core.util.HOLogger;

import javax.swing.*;
import java.awt.*;

public class SolarizedDarkTheme extends DarkTheme {

    public static final String THEME_NAME = "Solarized";

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return THEME_NAME;
    }

    @Override
    public boolean loadTheme() {
        try {
            LafManager.setTheme(new com.github.weisj.darklaf.theme.SolarizedDarkTheme());
            UIManager.setLookAndFeel(DarkLaf.class.getCanonicalName());
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();

            final Color blueishColour = new Color(25, 85, 100);

            // DEFAULT COLOR
            addColor(HOColorName.RED, defaults.getColor("palette.red"));
            addColor(HOColorName.BLUE, defaults.getColor("palette.blue"));
            addColor(HOColorName.GREEN, defaults.getColor("palette.green"));
            addColor(HOColorName.YELLOW, defaults.getColor("palette.yellow"));
            addColor(HOColorName.ORANGE, defaults.getColor("palette.orange"));
            addColor(HOColorName.URL_PANEL_BG, new Color(230, 174, 239));

            // Use defaults from LAF
            addColor(HOColorName.TABLEENTRY_FG, defaults.getColor("Label.foreground"));
            addColor(HOColorName.LABEL_FG, defaults.getColor("Label.foreground"));
            addColor(HOColorName.PANEL_BG, defaults.getColor("background"));
            addColor(HOColorName.BACKGROUND_CONTAINER, blueishColour);
            addColor(HOColorName.TABLEENTRY_BG, blueishColour);

            addColor(HOColorName.TABLE_SELECTION_FG, Color.WHITE);
            addColor(HOColorName.TABLE_SELECTION_BG, new Color(65, 65, 65));

            addColor(HOColorName.PLAYER_SKILL_SPECIAL_BG, new Color(56, 76, 53));
            addColor(HOColorName.PLAYER_SKILL_BG, new Color(95, 86, 38));
            addColor(HOColorName.PLAYER_POS_BG, new Color(55, 71, 83));
            addColor(HOColorName.PLAYER_SUBPOS_BG, new Color(60, 60, 60));

            // League Details
            // defaults defined by darklaf
            addColor(HOColorName.LEAGUE_TITLE_BG, defaults.getColor("TableHeader.background"));
            addColor(HOColorName.TABLE_LEAGUE_EVEN, defaults.getColor("Table.background"));
            addColor(HOColorName.TABLE_LEAGUE_ODD, defaults.getColor("Table.backgroundAlternative"));
            addColor(HOColorName.LEAGUE_FG, defaults.getColor("Table.foreground"));
            addColor(HOColorName.LEAGUE_BG, defaults.getColor("Table.background"));

            // Transfer module
            addColor(HOColorName.TRANSFER_IN_COLOR, defaults.getColor("palette.lime"));
            addColor(HOColorName.TRANSFER_OUT_COLOR, defaults.getColor("palette.red"));

            // Lineup
//            addColor(HOColorName.LINEUP_RATING_BORDER, Color.GRAY);
            addColor(HOColorName.LINEUP_PLAYER_SELECTED, new Color(0, 0, 0));
            addColor(HOColorName.LINEUP_PLAYER_SUB, new Color(10, 9, 79));
            addColor(HOColorName.RATING_BORDER_BELOW_LIMIT, defaults.getColor("palette.red"));
            addColor(HOColorName.RATING_BORDER_ABOVE_LIMIT, defaults.getColor("palette.blue"));
            addColor(HOColorName.LINEUP_HIGHLIGHT_FG, new Color(224, 255, 255));
            addColor(HOColorName.START_ASSISTANT, defaults.getColor("palette.forest"));
            addColor(HOColorName.CLEAR_LINEUP, defaults.getColor("palette.red"));
            addColor(HOColorName.LINEUP_COLOR, defaults.getColor("palette.gray"));

            // Matches
            addColor(HOColorName.MATCHTYPE_LEAGUE_BG, new Color(95, 86, 38));
            addColor(HOColorName.MATCHTYPE_BG, new Color(60, 60, 60));
            addColor(HOColorName.MATCHTYPE_FRIENDLY_BG, new Color(60, 63, 65));
            addColor(HOColorName.MATCHTYPE_INTFRIENDLY_BG, new Color(60, 63, 65));
//            addColor(HOColorName.MATCHTYPE_INT_BG, new Color(50, 67, 67));
            addColor(HOColorName.MATCHTYPE_CUP_BG, new Color(56, 76, 53));
            addColor(HOColorName.MATCHTYPE_QUALIFIKATION_BG, new Color(83, 45, 45));
            addColor(HOColorName.MATCHTYPE_MASTERS_BG, new Color(80, 70, 43));
            addColor(HOColorName.MATCHTYPE_NATIONAL_BG, new Color(57, 54, 62));
            addColor(HOColorName.MATCHTYPE_TOURNAMENT_GROUP_BG, new Color(48, 54, 56));
            addColor(HOColorName.MATCHTYPE_TOURNAMENT_FINALS_BG, new Color(61, 67, 68));
            addColor(HOColorName.MATCHTYPE_DIVISIONBATTLE_BG, new Color(66, 68, 80));

            //Training
            addColor(HOColorName.TRAINING_BIRTHDAY_BG, new Color(66, 66, 24));
            addColor(HOColorName.TRAINING_ICON_COLOR_1, defaults.getColor("Label.foreground"));
            addColor(HOColorName.TRAINING_ICON_COLOR_2, defaults.getColor("background").brighter());

            // Statistics
            addColor(HOColorName.STAT_PANEL_BG, defaults.getColor("background").brighter());
            addColor(HOColorName.STAT_PANEL_FG, defaults.getColor("Label.foreground"));

            // TS Forecast
            addColor(HOColorName.TSFORECAST_ALT_COLOR, new Color(160, 160, 210));

            // HRF Explorer
            addColor(HOColorName.HRF_GREEN_BG, new Color(56, 76, 53));
            addColor(HOColorName.HRF_LIGHTBLUE_BG, new Color(55, 71, 83));
            addColor(HOColorName.HRF_DARKBLUE_BG, new Color(25, 25, 68));
            addColor(HOColorName.HRF_RED_BG, new Color(68, 40, 40));

            // Smileys
            addColor(HOColorName.SMILEYS_COLOR, defaults.getColor("Label.foreground"));

            // Player Specialties
            addColor(HOColorName.PLAYER_SPECIALTY_COLOR, defaults.getColor("Label.foreground"));

            // palette
            addColor(HOColorName.PALETTE13_0, new Color(0, 255, 0));
            addColor(HOColorName.PALETTE13_1, new Color(255, 215, 0));
            addColor(HOColorName.PALETTE13_2, new Color(240, 32, 219));
            addColor(HOColorName.PALETTE13_3, new Color(255, 255, 255));
            addColor(HOColorName.PALETTE13_4, new Color(0, 255, 255));
            addColor(HOColorName.PALETTE13_5, new Color(200, 247, 197));
            addColor(HOColorName.PALETTE13_6, new Color(249, 140, 122));
            addColor(HOColorName.PALETTE13_7, new Color(0, 0, 0));
            addColor(HOColorName.PALETTE13_8, new Color(220, 198, 224));
            addColor(HOColorName.PALETTE13_9, new Color(255, 51, 51));
            addColor(HOColorName.PALETTE13_10, new Color(42, 161, 92));
            addColor(HOColorName.PALETTE13_11, new Color(255, 239, 153));
            addColor(HOColorName.PALETTE13_12, new Color(169, 169, 169));

            //training bars
            addColor(HOColorName.FULL_TRAINING_DONE, new Color(186, 247, 60));
            addColor(HOColorName.PARTIAL_TRAINING_DONE, new Color(245, 171, 53));
            addColor(HOColorName.FULL_STAMINA_DONE, new Color(173, 216, 230));

            // borders training position in lineup
            addColor(HOColorName.PLAYER_POSITION_PANEL_BORDER, ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
            addColor(HOColorName.LINEUP_FULL_TRAINING, new Color(240, 32, 219));
            addColor(HOColorName.LINEUP_PARTIAL_TRAINING, new Color(249, 140, 122));

            //players
            addColor(HOColorName.TABLEENTRY_DECLINE_FG, new Color(231, 144, 60));

            //players details
            addColor(HOColorName.PLAYER_DETAILS_BAR_BORDER_COLOR, defaults.getColor("Label.foreground"));
            addColor(HOColorName.PLAYER_DETAILS_BAR_FILL_GREEN, new Color(0, 255, 0));
            addColor(HOColorName.PLAYER_DETAILS_STARS_FILL, new Color(255, 215, 0));

            // League Details
            addColor(HOColorName.SHOW_MATCH, defaults.getColor("palette.lime"));
            addColor(HOColorName.DOWNLOAD_MATCH, defaults.getColor("palette.red"));
//            addColor(HOColorName.LEAGUEHISTORY_GRID_FG, defaults.getColor("background").brighter());
            addColor(HOColorName.LEAGUEHISTORY_CROSS_FG, defaults.getColor("background").brighter());
            addColor(HOColorName.HOME_TEAM_FG, new Color(252, 99, 153));
            addColor(HOColorName.SELECTED_TEAM_FG, new Color(82, 179, 217));
            addColor(HOColorName.LEAGUE_PANEL_BG, defaults.getColor("background").brighter());

            addColor(HOColorName.LINK_LABEL_FG, new Color(226,236,248));



            return super.enableTheme();
        } catch (Exception e) {
            HOLogger.instance().warning(getClass(),
                    String.format("Error loading %s: %s",
                            THEME_NAME,
                            e.getMessage()
                    ));
            return false;
        }
    }
}
