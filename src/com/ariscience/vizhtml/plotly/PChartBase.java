package com.ariscience.vizhtml.plotly;

/**
 * All chart providers in plotly package extend this class
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 * </p>
 */
public abstract class PChartBase {

    /**
     * Chart default width
     */
    public static final int S_WIDTH = 900;

    /**
     * Chart default height
     */
    public static final int S_HEIGHT = 500;

    /**
     * Chart default title
     */
    public static final String S_TITLE = "Chart";

    /**
     * Chart default X Axis tile
     */
    public static final String S_XTITLE = "X";

    /**
     * Chart default Y Axis title
     */
    public static final String S_YTITLE = "Y";

    /**
     * Chart default Z Axis title
     */
    public static final String S_ZTITLE = "Z";

    /**
     * Chart default Footnote
     */
    public static final String S_FOOTNOTE = "Footnote-1";

    /**
     * Chart footnote
     */
    private String m_footnote;

    /**
     * Chart template string
     */
    private static String m_templateString;
    /**
     * Sets Chart Footnote
     *
     * @param footnote Chart footnote
     */
    public void setFootnote(String footnote) {
        m_footnote = footnote;
    }

    /**
     * Gets Chart Footnote
     * @return Footnote
     */
    public String getFootnote() {
        return m_footnote;
    }

    /**
     * Gets Chart Template String
     * @return Template String
     */
    public static String getTemplateString() {
        return m_templateString;
    }

    /**
     * Sets Chart Template String
     * @param templateString the template string to use for all instances
     */
    public static void setTemplateString(String templateString) {
        m_templateString = templateString;
    }
}
