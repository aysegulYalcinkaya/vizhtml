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
 * Class for 3D Scatter Plot Charts
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class ScatterChart3D extends PChartBase {

    static {
        try {
            // Get template
            InputStream inputStream = ScatterChart3D.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/plotly/scatterPlotTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:" + e.getMessage());
        }
    }

    /**
     * X Axis data
     */
    private Number[][] m_xData;

    /**
     * Y Axis data
     */
    private Number[][] m_yData;

    /**
     * Z Axis data
     */
    private Number[][] m_zData;

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
     * Z Axis title
     */
    private String m_zAxisTitle;

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
     * Constructor for 3D Scatter Plot Chart
     * <p>
     * Chart title, width, height, X axis title, Y axis title, Z axis title are set to default values
     * <p>
     * Default values can be set through setter functions
     *
     * @param x_Data Values for X Axis
     * @param y_Data Values for Y Axis
     * @param z_Data Values for Z Axis
     */
    public ScatterChart3D(final Number[][] x_Data, final Number[][] y_Data, final Number[][] z_Data, String[][] info) {
        m_xData = x_Data;
        m_yData = y_Data;
        m_zData = z_Data;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        m_xAxisTitle = S_XTITLE;
        m_yAxisTitle = S_YTITLE;
        m_zAxisTitle = S_ZTITLE;
        setFootnote(S_FOOTNOTE);
        if (info == null) {
            this.m_info = new String[m_xData.length][m_xData[0].length];
            for (int i = 0; i < m_xData.length; i++) {
                Arrays.fill(this.m_info[i], "");
            }
        } else {
            this.m_info = info;
        }
    }

    /**
     * Constructor for 3D Scatter Plot Chart
     * <p>
     * Chart title, width, height, X axis title, Y axis title, Z axis title are set to default values
     * <p>
     * Default values can be set through setter functions
     *
     * @param x_Data Values for X Axis
     * @param y_Data Values for Y Axis
     * @param z_Data Values for Z Axis
     */
    public ScatterChart3D(final Number[][] x_Data, final Number[][] y_Data, final Number[][] z_Data) {
        this(x_Data, y_Data, z_Data, null);

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
     * Sets chart Z Data
     *
     * @param zData Chart Z Data
     */
    public void setZData(final Number[][] zData) {
        m_zData = zData;
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
     * Sets Z Axis Title
     *
     * @param zAxis_title Z axis title
     */
    public void setZAxisTitle(String zAxis_title) {
        m_zAxisTitle = zAxis_title;
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

        if (m_zData == null) {
            throw new NullLabelException("Z Data cannot be null!");
        }

        if (m_yData.length != m_xData.length) {
            throw new InconsistentDataException("Wrong number of Series Label or Data");
        }

        if (m_yData[0].length != m_xData[0].length || m_yData[0].length != m_zData[0].length) {
            throw new InconsistentDataException("Wrong number of xData, yData or zData");
        }

    }

    /**
     * <pre>
     * Generates Chart String based on template/plotly/scatterPlotTemplate.html
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
        String zData = "[";

        for (int i = 0; i < m_xData.length; i++) {
            xData += "[";
            for (int j = 0; j < m_xData[i].length; j++) {
                xData += "'" + m_xData[i][j] + "',";
            }
            xData = xData.substring(0, xData.length() - 1) + "],";

        }
        xData = xData.substring(0, xData.length() - 1) + "]";

        for (int i = 0; i < m_yData.length; i++) {
            yData += "[";
            for (int j = 0; j < m_yData[i].length; j++) {
                yData += "'" + m_yData[i][j] + "',";
            }
            yData = yData.substring(0, yData.length() - 1) + "],";

        }
        yData = yData.substring(0, yData.length() - 1) + "]";

        for (int i = 0; i < m_zData.length; i++) {
            zData += "[";
            for (int j = 0; j < m_xData[i].length; j++) {
                zData += "'" + m_zData[i][j] + "',";
            }
            zData = zData.substring(0, zData.length() - 1) + "],";

        }
        zData = zData.substring(0, zData.length() - 1) + "]";

        String info = "[";
        for (int i = 0; i < m_info.length; i++) {
            info += "[";
            for (int j = 0; j < m_info[i].length; j++) {
                info += "'" + m_info[i][j] + "',";
            }
            info = info.substring(0, info.length() - 1) + "],";
        }
        info = info.substring(0, info.length() - 1) + "]";

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("TITLE_X", m_xAxisTitle);
        replaceMap.put("DATAX", xData);
        replaceMap.put("DATAY", yData);
        replaceMap.put("DATAZ", zData);
        replaceMap.put("INFO", info);
        replaceMap.put("WIDTH", String.valueOf(m_width));
        replaceMap.put("HEIGHT", String.valueOf(m_height));
        replaceMap.put("TITLE_CHART", m_title);
        replaceMap.put("TITLE_Y", m_yAxisTitle);
        replaceMap.put("TITLE_Z", m_zAxisTitle);
        replaceMap.put("FOOTNOTE", getFootnote());

        // Replace each map key in template file with map value
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            chartStr = chartStr.replaceAll("\\$" + e.getKey() + "\\$", e.getValue());
        }

        return chartStr;
    }
}
