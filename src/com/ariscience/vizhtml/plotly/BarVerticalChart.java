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
 * Vertical Bar Chart Class
 * <p>
 * Uses same template with Horizontal Bar Chart
 * </p><p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 * </p>
 */
public class BarVerticalChart extends PChartBase {

    /**
     * Labels on X Axis
     */
    private String[][] m_xLabels;
    /**
     * Data for corresponding X labels
     */
    private Number[][] m_yData;

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
     * Chart type - "v"
     */
    private final String m_type;

    static {
        try {
            // Get template
            InputStream inputStream = BarVerticalChart.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/plotly/barTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:" + e.getMessage());
        }
    }

    /**
     * Constructor for Vertical Bar Chart
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels Label values on X Axis
     * @param y_Data   Values for X Axis Labels
     * @param info     Optional information strings for bar
     */
    public BarVerticalChart(final String[][] x_Labels, final Number[][] y_Data, final String[][] info) {
        m_xLabels = x_Labels;
        m_yData = y_Data;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        m_xAxisTitle = S_XTITLE;
        m_yAxisTitle = S_YTITLE;
        m_type = "v";
        setFootnote(S_FOOTNOTE);
        if (info==null){
            this.m_info=new String[1][m_xLabels[0].length];
            Arrays.fill(this.m_info[0], "");
        }
        else {
            this.m_info = info;
        }
    }

    /**
     * Constructor for Vertical Bar Chart
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels Label values on X Axis
     * @param y_Data   Values for X Axis Labels
     */
    public BarVerticalChart(final String[][] x_Labels, final Number[][] y_Data) {

        this(x_Labels, y_Data, null);

    }

    /**
     * Sets chart X Axis Labels
     *
     * @param xLabels X Axis Labels
     */
    public void setXLabels(final String[][] xLabels) {
        m_xLabels = xLabels;
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
     * @throws NullLabelException        if label provided is null
     * @throws InconsistentDataException if data provide is inconsistent with chart type
     */
    private void checkData() throws NullLabelException, InconsistentDataException {
        if (m_xLabels == null) {
            throw new NullLabelException("X Label cannot be null!");
        }

        if (m_yData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }

        if (m_yData[0].length != m_xLabels[0].length) {
            throw new InconsistentDataException("Wrong number of xAxis Labels or Data");
        }

        if (m_info[0].length != m_xLabels[0].length) {
            throw new InconsistentDataException("Wrong number of xAxis Labels or information strings");
        }
    }

    /**
     * Generates Vertical Bar Chart String based on template/plotly/barTemplate.html
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

        for (String xLabel : m_xLabels[0]) {
            columns += "'" + xLabel + "',";
        }
        columns = columns.substring(0, columns.length() - 1) + "]";

        for (int i = 0; i < m_yData[0].length; i++) {
            data += m_yData[0][i] + ",";
        }
        data = data.substring(0, data.length() - 1) + "]";


        for (int i = 0; i < m_info[0].length; i++) {
            info += "'"+m_info[0][i] + "',";
        }
        info = info.substring(0, info.length() - 1) + "]";


        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("TITLE_X", m_xAxisTitle);
        replaceMap.put("DATAX", columns);
        replaceMap.put("DATAY", data);
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

