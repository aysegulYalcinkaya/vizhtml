package com.ariscience.vizhtml.gchart;

import com.ariscience.vizhtml.VizHTMLUtils;
import com.ariscience.vizhtml.error.InconsistentDataException;
import com.ariscience.vizhtml.error.NullLabelException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Bubble Chart Class
 *
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class BubbleChart extends GChartBase {

    /**
     * Label for bubble - this labels are not mandatory
     */
    private String[][] m_xLabels;
    /**
     * X data value (1st dimension in bubble chart)
     */
    private Number[][] m_xData;
    /**
     * Data values for bubble chart
     * m_yData[0][] is Y axis data values (2nd dimension in bubble chart)
     */
    private Number[][] m_yData;

    /**
     * Data values for bubble chart
     * m_zData[0][] is Z axis data values (4th dimension in bubble chart)
     */
    private Number[][] m_zData;

    /**
     * Series labels can be null. This is the 3rd dimension in bubble chart
     */
    private String[] m_seriesLabels;

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
     * Series title
     */
    private String m_seriestitle;

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
            InputStream inputStream = BubbleChart.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/gchart/bubbleTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:"+e.getMessage());
        }

    }
    /**
     * Constructor for Bubble Chart
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param xLabels      Labels for data
     * @param x_Data       Values for X Data
     * @param y_Data       Values for Y Data
     * @param z_Data       Values for Z Data
     * @param seriesLabels Series Labels
     */
    public BubbleChart(final String[][] xLabels, final Number[][] x_Data, final Number[][] y_Data, final Number[][] z_Data, String[] seriesLabels) {
        m_xLabels = xLabels;
        m_xData = x_Data;
        m_yData = y_Data;
        m_zData = z_Data;
        m_seriesLabels = seriesLabels;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        m_xAxisTitle = S_XTITLE;
        m_yAxisTitle = S_YTITLE;
        m_zAxisTitle = S_ZTITLE;
        m_seriestitle = S_SERIESTITLE;
        this.setFootnote(S_FOOTNOTE);

    }

    /**
     * Constructor for Bubble Chart without series labels
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param xLabels Labels for data
     * @param x_Data  Values for X Data
     * @param y_Data  Values for Y Data
     * @param z_Data  Values for Z Data
     */
    public BubbleChart(final String[][] xLabels, final Number[][] x_Data, final Number[][] y_Data, final Number[][] z_Data) {
        this(xLabels, x_Data, y_Data, z_Data, null);
    }

    /**
     * Constructor for Bubble Chart without xLabels and series labels
     * Chart title, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Data Values for X Data
     * @param y_Data Values for Y Data
     * @param z_Data Values for Z Data
     */
    public BubbleChart(final Number[][] x_Data, final Number[][] y_Data, final Number[][] z_Data) {
        this(null, x_Data, y_Data, z_Data, null);
        String[][] xLabels = new String[1][x_Data[0].length];
        Arrays.fill(xLabels[0], "");
        m_xLabels = xLabels;
    }

    /**
     * <pre>
     * Constructor for Bubble Chart without series labels
     *
     * Chart title, width, height, X axis title, Y axis title are set to default values
     *
     * Default values can be set through setter functions
     * </pre>
     *
     * @param x_Data       Values for X Data
     * @param y_Data       Values for Y Data
     * @param z_Data       Determines the bubble size
     * @param seriesLabels Series Labels
     */
    public BubbleChart(final Number[][] x_Data, final Number[][] y_Data, final Number[][] z_Data, String[] seriesLabels) {
        this(null, x_Data, y_Data, z_Data, seriesLabels);
        String[][] xLabels = new String[1][x_Data[0].length];
        Arrays.fill(xLabels[0], "");
        m_xLabels = xLabels;
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
     * Sets chart series labels
     *
     * @param seriesLabels Chart series labels
     */
    public void setSeriesLabels(String[] seriesLabels) {
        m_seriesLabels = seriesLabels;
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
     * Sets Series Title
     *
     * @param series_title Series title
     */
    public void setSeriesTitle(String series_title) {
        m_seriestitle = series_title;
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
            throw new NullLabelException("X Label cannot be null!");
        }

        if (m_yData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }

        if (m_yData[0].length != m_xData[0].length) {
            throw new InconsistentDataException("Wrong number of xData or yData");
        }
        if (m_zData[0].length != m_xData[0].length) {
            throw new InconsistentDataException("Wrong number of xData or zData");
        }

    }

    /**
     * Generates Histogram Chart String based on template/gchart/histogramTemplate.html
     *
     * Tokens in template file is replaced with corresponding value
     *
     * @throws NullLabelException if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     * @return chart html string
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();

        checkData();

        String data = "['Name','" + m_xAxisTitle + "','" + m_yAxisTitle + "','";

        if (m_seriesLabels == null) {
            data += m_zAxisTitle + "'],";
            for (int i = 0; i < m_xData[0].length; i++) {
                data += "['" + m_xLabels[0][i] + "'," + m_xData[0][i] + "," + m_yData[0][i] + "," + m_zData[0][i] + "],";
            }
        } else {
            data += m_seriestitle + "','" + m_zAxisTitle + "'],";
            for (int i = 0; i < m_xData[0].length; i++) {
                data += "['" + m_xLabels[0][i] + "'," + m_xData[0][i] + "," + m_yData[0][i] + ",'" + m_seriesLabels[i] + "'," + m_zData[0][i] + "],";
            }
        }
        data = data.substring(0, data.length() - 1);

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("TITLE_X", m_xAxisTitle);
        replaceMap.put("DATA", data);
        replaceMap.put("WIDTH", String.valueOf(m_width));
        replaceMap.put("HEIGHT", String.valueOf(m_height));
        replaceMap.put("TITLE_CHART", m_title);
        replaceMap.put("TITLE_Y", m_yAxisTitle);
        replaceMap.put("FOOTNOTE",getFootnote());

        // Replace each map key in template file with map value
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            chartStr = chartStr.replaceAll("\\$" + e.getKey() + "\\$", e.getValue());
        }

        return chartStr;
    }

}

