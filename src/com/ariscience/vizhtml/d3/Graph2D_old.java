package com.ariscience.vizhtml.d3;

import com.ariscience.vizhtml.VizHTMLUtils;
import com.ariscience.vizhtml.error.InconsistentDataException;
import com.ariscience.vizhtml.error.NullLabelException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 2D graph layout
 * </pre>
 *
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class Graph2D_old extends D3GraphBase{

    /**
     * Data for X
     */
    private Number[][] m_xData;

    /**
     * Data for Y
     */
    private Number[][] m_yData;

    /**
     * Data for links in {{target1,source1},{target2,source2}} format
     */
    private Number[][] m_links;

    /**
     * Chart title
     */
    private String m_title;

    /**
     * Chart width (px)
     */
    private int m_width;

    /**
     * Chart height (px)
     */
    private int m_height;

    static {

        try {
            // Get template
            InputStream inputStream = Graph2D_old.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/d3/graph2D.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:"+e.getMessage());
        }

    }

    /**
     * Constructor for Horizontal Bar Chart
     * Chart title, subtitle, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Data        Values for X Data
     * @param y_Data        Values for Y Data
     * @param links         Values for links data in {source,target}
     */
    public Graph2D_old(final Number[][] x_Data, final Number[][] y_Data, final Number[][] links) {
        m_xData = x_Data;
        m_yData=y_Data;
        m_links=links;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        this.setFootnote(S_FOOTNOTE);
    }

    public void setXData(Number[][] m_xData) {
        this.m_xData = m_xData;
    }

    public void setYData(Number[][] m_yData) {
        this.m_yData = m_yData;
    }

    public void setLinks(Number[][] m_links) {
        this.m_links = m_links;
    }

    public void setTitle(String m_title) {
        this.m_title = m_title;
    }

    public void setWidth(int m_width) {
        this.m_width = m_width;
    }

    public void setHeight(int m_height) {
        this.m_height = m_height;
    }

    /**
     * This function is used before generating chart string to check null and
     * inconsistent data
     *
     * @throws NullLabelException        Exception for null labels
     * @throws InconsistentDataException Exception for inconsistent data
     */
    private void checkData() throws NullLabelException, InconsistentDataException {

        if (m_xData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }

        if (m_yData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }

        if (m_xData.length != m_yData.length) {
            throw new InconsistentDataException("Wrong number of X or Y Data");
        }

    }

    /**
     * Generates 2D Graph String based on template/d3/graph2D.html
     *
     * Tokens in template file is replaced with corresponding value
     *
     * @return chart html string
     * @throws NullLabelException if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();

        checkData();

        String nodes = "[";
        String links = "[";

        for (int i = 0; i < m_xData[0].length; i++) {
            nodes += "{'x':" + m_xData[0][i] + ", 'y':" + m_yData[0][i] +"},";
        }

        nodes = nodes.substring(0, nodes.length() - 1)+"]";

        for (int i = 0; i < m_links.length; i++) {
            links += "{'target':" + m_links[i][0] + ", 'source':" + m_links[i][1] +"},";
        }

        links = links.substring(0, links.length() - 1)+"]";

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("NODES", nodes);
        replaceMap.put("LINKS", links);
        replaceMap.put("WIDTH", String.valueOf(m_width));
        replaceMap.put("HEIGHT", String.valueOf(m_height));
        replaceMap.put("TITLE_CHART", m_title);
        replaceMap.put("FOOTNOTE",getFootnote());

        // Replace each map key in template file with map value
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            chartStr = chartStr.replaceAll("\\$" + e.getKey() + "\\$", e.getValue());
        }

        return chartStr;
    }

}
