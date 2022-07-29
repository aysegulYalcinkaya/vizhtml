package com.ariscience.vizhtml.gchart;

import com.ariscience.vizhtml.VizHTMLUtils;
import com.ariscience.vizhtml.error.InconsistentDataException;
import com.ariscience.vizhtml.error.NullLabelException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Pie Chart Class
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class PieChart extends GChartBase {


    /**
     * Chart data info labels
     */
    private String[][] m_xLabels;
    /**
     * Chart data labels
     */
    private String[][] m_yLabels;
    /**
     * Chart data
     */
    private Number[][] m_yData;
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
            InputStream inputStream = PieChart.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/gchart/pieTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        }
        catch (IOException e){
            throw new RuntimeException("Could not instantiate due to IOE:"+e.getMessage());
        }
    }
    /**
     * Constructor for Pie Chart
     * Chart title, width and height are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels Pie data info labels
     * @param y_Labels Pie Data Labels
     * @param y_Data   Data values
     */
    public PieChart(final String[][] x_Labels, String[][] y_Labels, final Number[][] y_Data) {
        m_xLabels = x_Labels;
        m_yLabels = y_Labels;
        m_yData = y_Data;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        setFootnote(S_FOOTNOTE);

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
     * Sets chart Y Axis Labels
     *
     * @param yLabels Y Axis Labels
     */
    public void setYLabels(final String[][] yLabels) {
        m_yLabels = yLabels;
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
     * This function is used before generating chart string to check null and
     * inconsistent data
     *
     * @throws NullLabelException        Exception for null labels
     * @throws InconsistentDataException Exception for inconsistent data
     */
    private void checkData() throws NullLabelException, InconsistentDataException {
        if (m_xLabels == null) {
            throw new NullLabelException("X Label cannot be null!");
        }
        if (m_yLabels == null) {
            throw new NullLabelException("Y Label cannot be null!");
        }
        if (m_yData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }
        if (m_yData[0].length != m_yLabels[0].length) {
            throw new InconsistentDataException("Wrong number of Y Label or Y Data");
        }
        if (m_xLabels[0].length != 2) {
            throw new InconsistentDataException("Wrong number of X Label");
        }
    }

    /**
     * Generates Pie Chart String based on template/gchart/pieTemplate.html
     *
     * Tokens in template file is replaced with corresponding value
     *
     * @return chart html string
     * @throws NullLabelException if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr=getTemplateString();

        checkData();

        String columns = "[";
        String data = "";

        for (String name : m_xLabels[0]) {
            columns += "'" + name + "',";
        }

        columns = columns.substring(0, columns.length() - 1) + "]";

        for (int i = 0; i < m_yLabels[0].length; i++) {
            data += "['" + m_yLabels[0][i] + "'," + m_yData[0][i] + "], ";
        }

        data = data.substring(0, data.length() - 2);

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("DATAX", columns);
        replaceMap.put("DATAY", data);
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

