package com.ariscience.vizhtml.plotly;

import com.ariscience.vizhtml.VizHTMLUtils;
import com.ariscience.vizhtml.error.InconsistentDataException;
import com.ariscience.vizhtml.error.NullLabelException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Horizontal Bar Chart Class.
 * <p>
 * Uses same template with Horizontal Bar Chart
 * </p><p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 * </p>
 */
public class BarHorizontalChart extends PChartBase {

    /**
     * Labels on Y Axis
     */
    private String[][] m_yLabels;

    /**
     * Data for corresponding Y Labels
     */
    private Number[][] m_xData;

    /**
     * Optional information string for bar
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
     * Chart type - "h"
     */
    private final String m_type;

    static {
        try {
            // Get template
            InputStream inputStream = BarHorizontalChart.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/plotly/barTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:" + e.getMessage());
        }
    }

    /**
     * Constructor for Horizontal Bar Chart
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param y_Labels Label values on Y Axis
     * @param x_Data   Values for Y Axis Labels
     * @param info     Optional information strings for bar
     */
    public BarHorizontalChart(final String[][] y_Labels, final Number[][] x_Data, String[][] info) {
        m_yLabels = y_Labels;
        m_xData = x_Data;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        m_xAxisTitle = S_XTITLE;
        m_yAxisTitle = S_YTITLE;
        m_type = "h";
        setFootnote(S_FOOTNOTE);
        if (info == null) {
            this.m_info = new String[1][m_yLabels[0].length];
            Arrays.fill(this.m_info[0], "");
        } else {
            this.m_info = info;
        }

    }

    /**
     * Constructor for Horizontal Bar Chart
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param y_Labels Label values on Y Axis
     * @param x_Data   Values for Y Axis Labels
     */
    public BarHorizontalChart(final String[][] y_Labels, final Number[][] x_Data) {
        this(y_Labels, x_Data, null);

    }

    /**
     * Sets chart Y Axis Labels
     *
     * @param yLabels Y Axis Labels
     */
    public void setYLabels(final String[][] yLabels) {
        m_yLabels = yLabels;
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
     * Sets optional information strings
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
     * This function is used before generating chart string to check null and inconsistent data
     *
     * @throws NullLabelException        Exception for null labels
     * @throws InconsistentDataException Exception for inconsistent data
     */
    private void checkData() throws NullLabelException, InconsistentDataException {
        if (m_yLabels == null) {
            throw new NullLabelException("Y Label cannot be null!");
        }

        if (m_xData == null) {
            throw new NullLabelException("X Data cannot be null!");
        }

        if (m_xData[0].length != m_yLabels[0].length) {
            throw new InconsistentDataException("Wrong number of yAxis Labels or Data");
        }
    }

    /**
     * Generates Horizontal Bar Chart String based on template/plotly/barTemplate.html
     * <p>
     * Tokens in template file is replaced with corresponding value
     *
     * @return chart html string
     * @throws NullLabelException        if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();


        checkData();

        String columns = "[";
        String data = "[";
        String info = "[";

        for (String yLabel : m_yLabels[0]) {
            columns += "'" + yLabel + "',";
        }
        columns = columns.substring(0, columns.length() - 1) + "]";

        for (int i = 0; i < m_xData[0].length; i++) {
            data += m_xData[0][i] + ",";
        }
        data = data.substring(0, data.length() - 1) + "]";

        for (int i = 0; i < m_info[0].length; i++) {
            info += "'"+m_info[0][i] + "',";
        }
        info = info.substring(0, info.length() - 1) + "]";

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("TITLE_X", m_xAxisTitle);
        replaceMap.put("DATAX", data);
        replaceMap.put("DATAY", columns);
        replaceMap.put("INFO", info);
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

