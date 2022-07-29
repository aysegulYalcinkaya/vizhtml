package com.ariscience.vizhtml.plotly;

import com.ariscience.vizhtml.error.InconsistentDataException;
import com.ariscience.vizhtml.error.NullLabelException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Class for Line &amp; Scatter Charts
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public abstract class LineScatterChartBase extends PChartBase {

    /**
     * X Axis data
     */
    private Number[][] m_xData;

    /**
     * Y Axis data
     */
    private Number[][] m_yData;

    /**
     * Optional information string
     */
    private String[][] m_info;
    /**
     * X Axis title
     */
    private String m_xAxisTitle;

    /**
     * Y Axis title
     */
    private String m_yAxisTitle;

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

    /**
     * Chart type - "lines" or "markers"
     */
    private String m_type;

    /**
     * Constructor for Line &amp; Scatter Chart
     * <p>
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * <p>
     * Default values can be set through setter functions
     *
     * @param x_Data Values for X Axis
     * @param y_Data Values for Y Axis
     * @param info   Optional information strings
     */
    public LineScatterChartBase(final Number[][] x_Data, final Number[][] y_Data, String[][] info) {
        m_xData = x_Data;
        m_yData = y_Data;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        m_xAxisTitle = S_XTITLE;
        m_yAxisTitle = S_YTITLE;
        setFootnote(S_FOOTNOTE);
        if (info == null) {
            this.m_info = new String[1][m_xData[0].length];
            Arrays.fill(this.m_info[0], "");
        } else {
            this.m_info = info;
        }
    }

    /**
     * Constructor for Line &amp; Scatter Chart
     * <p>
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * <p>
     * Default values can be set through setter functions
     *
     * @param x_Data Values for X Axis
     * @param y_Data Values for Y Axis
     */
    public LineScatterChartBase(final Number[][] x_Data, final Number[][] y_Data) {
        this(x_Data,y_Data,null);
    }

    /**
     * Sets chart X Data
     *
     * @param xData Chart X Data
     */
    public void setXData(final Number[][] xData) {
        m_xData = xData;
    }

    /**
     * Sets chart Y Data
     *
     * @param yData Chart Y Data
     */
    public void setYData(final Number[][] yData) {
        m_yData = yData;
    }

    /**
     * Sets optional information strings
     *
     * @param m_info Info String
     */
    public void set_info(String[][] m_info) {
        this.m_info = m_info;
    }

    /**
     * Sets chart title
     *
     * @param title Chart title
     */
    public void setTitle(String title) {
        m_title = title;
    }

    /**
     * Sets chart width
     *
     * @param width Chart width (px)
     */
    public void setWidth(final int width) {
        m_width = width;
    }

    /**
     * Sets chart height
     *
     * @param height Chart height (px)
     */
    public void setHeight(final int height) {
        m_height = height;
    }

    /**
     * Sets X Axis Title
     *
     * @param xAxis_title X axis title
     */
    public void setXAxisTitle(String xAxis_title) {
        m_xAxisTitle = xAxis_title;
    }

    /**
     * Sets Y Axis Title
     *
     * @param yAxis_title Y axis title
     */
    public void setYAxisTitle(String yAxis_title) {
        m_yAxisTitle = yAxis_title;
    }

    /**
     * Sets chart type
     *
     * @param type "lines" or "markers"
     */
    public void setType(String type) {
        m_type = type;
    }

    /**
     * This function is used before generating chart string to check null and
     * inconsistent data
     *
     * @throws NullLabelException        Exception for null labels
     * @throws InconsistentDataException Exception for inconsistent data
     */
    void checkData() throws NullLabelException, InconsistentDataException {
        if (m_xData == null) {
            throw new NullLabelException("X Label cannot be null!");
        }

        if (m_yData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }

        if (m_yData.length != m_xData.length) {
            throw new InconsistentDataException("Wrong number of Series Label or Data");
        }

        if (m_yData[0].length != m_xData[0].length) {
            throw new InconsistentDataException("Wrong number of xData or yData");
        }

    }

    /**
     * <pre>
     * Generates Chart String based on template/plotly/lineScatterTemplate.html
     *
     * Tokens in template file is replaced with corresponding value
     * </pre>
     *
     * @return chart html string
     * @throws NullLabelException        if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();

        checkData();

        String xData = "[";
        String yData = "[";
        String info = "[";

        for (int i = 0; i < m_xData[0].length; i++) {
            xData += m_xData[0][i] + ",";
        }

        xData = xData.substring(0, xData.length() - 1) + "]";

        for (int i = 0; i < m_yData[0].length; i++) {
            yData += m_yData[0][i] + ",";
        }

        yData = yData.substring(0, yData.length() - 1) + "]";

        for (int i = 0; i < m_info[0].length; i++) {
            info += "'"+m_info[0][i] + "',";
        }

        info = info.substring(0, info.length() - 1) + "]";

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("TITLE_X", m_xAxisTitle);
        replaceMap.put("DATAX", xData);
        replaceMap.put("DATAY", yData);
        replaceMap.put("INFO",info);
        replaceMap.put("WIDTH", String.valueOf(m_width));
        replaceMap.put("HEIGHT", String.valueOf(m_height));
        replaceMap.put("TITLE_CHART", m_title);
        replaceMap.put("TITLE_Y", m_yAxisTitle);
        replaceMap.put("TYPE", m_type);
        replaceMap.put("FOOTNOTE", getFootnote());

        // Replace each map key in template file with map value
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            chartStr = chartStr.replaceAll("\\$" + e.getKey() + "\\$", e.getValue());
        }

        return chartStr;
    }
}
