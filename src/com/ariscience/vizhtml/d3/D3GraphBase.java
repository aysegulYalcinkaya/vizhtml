package com.ariscience.vizhtml.d3;

import com.ariscience.vizhtml.common.GraphD;

/**
 * <pre>
 * Base D3 charts and graphs
 * </pre>
 *
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class D3GraphBase{

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
     *
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
     * @param templateString set the template string to use
     */
    public static void setTemplateString(String templateString) {
        m_templateString = templateString;
    }
}
