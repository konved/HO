package module.teamAnalyzer.vo;

import core.model.HOVerwaltung;
import core.model.Ratings;
import core.model.match.IMatchDetails;
import core.model.match.Matchdetails;

import java.util.Map;


/**
 * Class with holds the Ratings of a team in the 7 areas of the field
 */
public class MatchRating {


    public static int ERROR = -9999;


    //~ Instance fields ----------------------------------------------------------------------------

    private double centralAttack;
    private double centralDefense;
    private double leftAttack;
    private double leftDefense;
    private double midfield;
    private double rightAttack;
    private double rightDefense;

    private int tacticSkill;
    private int tacticType;
    private double HatStats;
    private double LoddarStat;
    private int attitude;
    private int style_of_play;

    public int getTacticSkill() {
        return tacticSkill;
    }

    public void setTacticSkill(int tacticSkill) {
        this.tacticSkill = tacticSkill;
    }

    public int getAttitude() {
        return attitude;
    }

    public int getStyle_of_play() {
        return style_of_play;
    }

    public void setAttitude(String attitude) {
        this.attitude = AttitudeStringToInt(attitude);
    }

    public static int AttitudeStringToInt(String attitude) {
        attitude = attitude.toLowerCase();
        HOVerwaltung hoi = HOVerwaltung.instance();

        if (attitude.equals(hoi.getLanguageString("ls.team.teamattitude.normal").toLowerCase())) return 0;
        else if (attitude.equals(hoi.getLanguageString("ls.team.teamattitude.playitcool").toLowerCase())) return -1;
        else if (attitude.equals(hoi.getLanguageString("ls.team.teamattitude.matchoftheseason").toLowerCase()))
            return 1;
        else return ERROR;
    }


    public int getTacticType() {
        return tacticType;
    }

    public void setTacticType(String tacticType) {
        this.tacticType = TacticTypeStringToInt(tacticType);
    }

    public static int TacticTypeStringToInt(String tacticType) {
        tacticType = tacticType.toLowerCase();
        HOVerwaltung hoi = HOVerwaltung.instance();
        if (tacticType.equals(hoi.getLanguageString("ls.team.tactic.normal").toLowerCase())) return 0;
        else if (tacticType.equals(hoi.getLanguageString("ls.team.tactic.pressing").toLowerCase())) return 1;
        else if (tacticType.equals(hoi.getLanguageString("ls.team.tactic.counter-attacks").toLowerCase())) return 2;
        else if (tacticType.equals(hoi.getLanguageString("ls.team.tactic.attackinthemiddle").toLowerCase())) return 3;
        else if (tacticType.equals(hoi.getLanguageString("ls.team.tactic.attackonwings").toLowerCase())) return 4;
        else if (tacticType.equals(hoi.getLanguageString("ls.team.tactic.playcreatively").toLowerCase())) return 7;
        else if (tacticType.equals(hoi.getLanguageString("ls.team.tactic.longshots").toLowerCase())) return 8;
        else return ERROR;
    }


    public void setStyle_of_play(String style_of_play) {
        this.style_of_play = StyleOfPlayStringToInt(style_of_play);
    }


    public static int StyleOfPlayStringToInt(String style_of_play) {
        //        -10	100% defensive
//                -9	90% defensive
//                -8	80% defensive
//                -7	70% defensive
//                -6	60% defensive
//                -5	50% defensive
//                -4	40% defensive
//                -3	30% defensive
//                -2	20% defensive
//                -1	10% defensive
//        0	Neutral
//        1	10% offensive
//        2	20% offensive
//        3	30% offensive
//        4	40% offensive
//        5	50% offensive
//        6	60% offensive
//        7	70% offensive
//        8	80% offensive
//        9	90% offensive
//        10	100% offensive
        style_of_play = style_of_play.toLowerCase();
        HOVerwaltung hoi = HOVerwaltung.instance();
        if (style_of_play.equals(hoi.getLanguageString("ls.team.styleofplay.neutral").toLowerCase())) return 0;
        else return ERROR;
    }


    public MatchRating() {
    }

    public MatchRating(Map<String, String> matchRating) {
        this.centralAttack = intHT2loatHT(Integer.parseInt(matchRating.get("RatingMidAtt")));
        this.centralDefense = intHT2loatHT(Integer.parseInt(matchRating.get("RatingMidDef")));
        this.leftAttack = intHT2loatHT(Integer.parseInt(matchRating.get("RatingLeftAtt")));
        this.leftDefense = intHT2loatHT(Integer.parseInt(matchRating.get("RatingLeftDef")));
        this.midfield = intHT2loatHT(Integer.parseInt(matchRating.get("RatingMidfield")));
        this.rightAttack = intHT2loatHT(Integer.parseInt(matchRating.get("RatingRightAtt")));
        this.rightDefense = intHT2loatHT(Integer.parseInt(matchRating.get("RatingRightDef")));
        this.tacticSkill = Integer.parseInt(matchRating.get("TacticSkill"));
        this.tacticType = Integer.parseInt(matchRating.get("TacticType"));
        HatStats = computeHatStats();
        LoddarStat = computeLoddarStats();
    }

    public MatchRating(double LD, double CD, double RD, double MF, double LA, double CA, double RA, int tacticType, int tacticSkill) {
        this.centralAttack = CA;
        this.centralDefense = CD;
        this.leftAttack = LA;
        this.leftDefense = LD;
        this.midfield = MF;
        this.rightAttack = RA;
        this.rightDefense = RD;
        this.tacticSkill = tacticSkill;
        this.tacticType = tacticType;
        this.HatStats = computeHatStats();
        this.LoddarStat = computeLoddarStats();
    }

    public MatchRating(double LD, double CD, double RD, double MF, double LA, double CA, double RA, int iAttitude,
                       int iTacticType, int iTacticlevel, int iStyle_of_play) {
        this.centralAttack = CA;
        this.centralDefense = CD;
        this.leftAttack = LA;
        this.leftDefense = LD;
        this.midfield = MF;
        this.rightAttack = RA;
        this.rightDefense = RD;
        this.tacticSkill = iTacticlevel;
        this.tacticType = iTacticType;
        this.attitude = iAttitude;
        this.style_of_play = iStyle_of_play;
        this.HatStats = computeHatStats();
        this.LoddarStat = computeLoddarStats();
    }

    public MatchRating(Ratings ratings) {
        this.centralAttack = ratings.getCentralAttack().get(0d);
        this.centralDefense = ratings.getCentralDefense().get(0d);
        this.leftAttack = ratings.getLeftAttack().get(0d);
        this.leftDefense = ratings.getLeftDefense().get(0d);
        this.midfield = ratings.getMidfield().get(0d);
        this.rightAttack = ratings.getRightAttack().get(0d);
        this.rightDefense = ratings.getRightDefense().get(0d);
        this.HatStats = ratings.getHatStats().get(0d);
        this.LoddarStat = ratings.getLoddarStat().get(0d);
    }

    //~ Methods ------------------------------------------------------------------------------------

    public MatchRating minus(MatchRating other) {
        if (null == other) return this;

        MatchRating diff = new MatchRating();
        diff.setCentralDefense(this.getCentralDefense() - other.getCentralDefense());
        diff.setRightDefense(this.getRightDefense() - other.getRightDefense());
        diff.setLeftDefense(this.getLeftDefense() - other.getLeftDefense());
        diff.setMidfield(this.getMidfield() - other.getMidfield());
        diff.setCentralAttack(this.getCentralAttack() - other.getCentralAttack());
        diff.setRightAttack(this.getRightAttack() - other.getRightAttack());
        diff.setLeftAttack(this.getLeftAttack() - other.getLeftAttack());
        diff.setHatStats(this.getHatStats() - other.getHatStats());
        diff.setLoddarStats(this.getLoddarStats() - other.getLoddarStats());

        return diff;
    }

    /**
     * Sets the central attack rating
     *
     * @param rating The rating
     */
    public final void setCentralAttack(double rating) {
        centralAttack = rating;
    }

    /**
     * Gets the central attack rating
     *
     * @return The rating
     */
    public final double getCentralAttack() {
        return centralAttack;
    }

    /**
     * Sets the central defense rating
     *
     * @param rating The rating
     */
    public final void setCentralDefense(double rating) {
        centralDefense = rating;
    }

    /**
     * Gets the central defense rating
     *
     * @return The rating
     */
    public final double getCentralDefense() {
        return centralDefense;
    }

    /**
     * Returns the calculated <B>HatStats</B><code>(3  midfield) + (sum of defence) + (sum of
     * attack )</code>
     *
     * @return the rating
     */
    public final double getHatStats() {
        return HatStats;
    }

    public final void setHatStats(double _HatStats) {
        HatStats = _HatStats;
    }

    /**
     * Sets the left attack rating
     *
     * @param rating The rating
     */
    public final void setLeftAttack(double rating) {
        leftAttack = rating;
    }

    /**
     * Gets the left attack rating
     *
     * @return The rating
     */
    public final double getLeftAttack() {
        return leftAttack;
    }

    /**
     * Sets the left defense rating
     *
     * @param rating The rating
     */
    public final void setLeftDefense(double rating) {
        leftDefense = rating;
    }

    /**
     * Gets the left defense rating
     *
     * @return The rating
     */
    public final double getLeftDefense() {
        return leftDefense;
    }


    public final void setLoddarStats(double _LoddarStats) {
        LoddarStat = _LoddarStats;
    }

    public final double getLoddarStats() {
        return LoddarStat;
    }


    public final int computeHatStats() {
        int mid = double2HTint(this.midfield);
        int def = double2HTint(this.leftDefense) + double2HTint(this.centralDefense) + double2HTint(this.rightDefense);
        int att = double2HTint(this.leftAttack) + double2HTint(this.centralAttack) + double2HTint(this.rightAttack);
        return 3 * mid + def + att;
    }


    public final double computeLoddarStats() {
        final double MIDFIELD_SHIFT = 0.0;
        final double COUNTERATTACK_WEIGHT = 0.25;
        final double DEFENSE_WEIGHT = 0.47;
        final double ATTACK_WEIGHT = 1 - DEFENSE_WEIGHT;
        final double CENTRAL_WEIGHT = 0.37;
        final double WINGER_WEIGTH = (1 - CENTRAL_WEIGHT) / 2d;

        double correctedCentralWeigth = CENTRAL_WEIGHT;

        switch (this.tacticType) {
            case IMatchDetails.TAKTIK_MIDDLE:
                correctedCentralWeigth = CENTRAL_WEIGHT + (((0.2 * (this.tacticSkill - 1)) / 19d) + 0.2);
                break;

            case IMatchDetails.TAKTIK_WINGS:
                correctedCentralWeigth = CENTRAL_WEIGHT - (((0.2 * (this.tacticSkill - 1)) / 19d) + 0.2);
                break;

            default:
                break;
        }

        final double correctedWingerWeight = (1 - correctedCentralWeigth) / 2d;

        double counterCorrection = 0;

        if (this.tacticType == IMatchDetails.TAKTIK_KONTER) {
            counterCorrection = (COUNTERATTACK_WEIGHT * 2 * this.tacticSkill) / (this.tacticSkill + 20);
        }

        // Calculate attack rating
        final double attackStrength = (ATTACK_WEIGHT + counterCorrection) * ((correctedCentralWeigth * hq(centralAttack))
                + (correctedWingerWeight * (hq(leftAttack) + hq(rightAttack))));

        // Calculate defense rating
        final double defenseStrength = DEFENSE_WEIGHT * ((CENTRAL_WEIGHT * hq(centralDefense))
                + (WINGER_WEIGTH * (hq(leftDefense) + hq(rightDefense))));

        // Calculate midfield rating
        final double midfieldFactor = MIDFIELD_SHIFT + ((1 - MIDFIELD_SHIFT) * hq(midfield));

        // Calculate and return the LoddarStats rating
        return 80 * midfieldFactor * (defenseStrength + attackStrength);
    }

    /**
     * Sets the midfield rating
     *
     * @param rating The rating
     */
    public final void setMidfield(double rating) {
        midfield = rating;
    }

    /**
     * Gets the midfield rating
     *
     * @return The rating
     */
    public final double getMidfield() {
        return midfield;
    }

    /**
     * Returns the calculated <B>PStats</B> (aka PeasoStats)
     *
     * @return the rating
     */
    public final double getPStats() {
        final double MIDFIELD = 0.46;
        final double ATTACK = 0.32;
        final double DEFENSE = 0.22;
        final double SIDE = 0.3;
        final double CENTER = 0.4;

        return (rightDefense * DEFENSE * SIDE) + (centralDefense * DEFENSE * CENTER)
                + (leftDefense * DEFENSE * SIDE) + (rightAttack * ATTACK * SIDE)
                + (centralAttack * ATTACK * CENTER) + (leftAttack * ATTACK * SIDE)
                + (midfield * MIDFIELD);
    }

    /**
     * Sets the right attack rating
     *
     * @param rating The rating
     */
    public final void setRightAttack(double rating) {
        rightAttack = rating;
    }

    /**
     * Gets the right attack rating
     *
     * @return The rating
     */
    public final double getRightAttack() {
        return rightAttack;
    }

    /**
     * Sets the right defense rating
     *
     * @param rating The rating
     */
    public final void setRightDefense(double rating) {
        rightDefense = rating;
    }

    /**
     * Gets the right defense rating
     *
     * @return The rating
     */
    public final double getRightDefense() {
        return rightDefense;
    }

    /**
     * Returns the calculated <B>Smart Squad Rating</B> (<code>Squad Rating / stars</code>)
     *
     * @param stars Team star rating
     * @return the rating
     */
    public final double getSmartSquad(double stars) {
        return getSquad() / stars;
    }

    /**
     * Returns the calculated <B>Squad Rating</B><code>(2  midfield) + (sum of defence) + (sum of
     * attack )</code>
     *
     * @return the rating
     */
    public final double getSquad() {
        return (midfield * 2) + leftAttack + rightAttack + centralAttack + centralDefense
                + leftDefense + rightDefense;
    }

    /**
     * Convert reduced float rating (1.00....20.99) to original integer HT rating (1...80) one +0.5
     * is because of correct rounding to integer
     *
     * @param x HO float rating
     * @return Integer HT rating
     */
    public static final int float2HTint(float x) {
        return (int) (((x - 1.0f) * 4.0f) + 1.0f);
    }

    public static final int double2HTint(double x) {
        return float2HTint((float) x);
    }

    public final double intHT2loatHT(int x) {
        return (double) (((x - 1.0f) / 4.0f) + 1.0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        final StringBuffer buffer = new StringBuffer();

        buffer.append("MatchRating[");
        buffer.append("midfield = " + midfield);
        buffer.append(", leftDefense = " + leftDefense);
        buffer.append(", centralDefense = " + centralDefense);
        buffer.append(", rightDefense = " + rightDefense);
        buffer.append(", leftAttack = " + leftAttack);
        buffer.append(", centralAttack = " + centralAttack);
        buffer.append(", rightAttack = " + rightAttack);
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * Hattrick Quality function
     */
    private double hq(double _value) {
        int value = double2HTint(_value);
        return (2.0f * value) / (value + 80.0f);
    }
}
