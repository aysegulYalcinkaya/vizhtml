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
 * Histogram Chart Class
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class HistogramChart extends PChartBase {
    /**
     * Series labels for multi series charts
     * Series1  m_xData[0][]
     * Series2 m_xData[1][]
     */
    private String[] m_seriesLabels;

    /**
     * Chart X Data
     */
    private Number[][] m_xData;
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

    static {
        try {
            // Get template
            InputStream inputStream = PChartBase.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/plotly/histogramTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:" + e.getMessage());
        }
    }

    /**
     * Constructor for Pie Chart
     * Chart title, width and height are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Data        Data values
     * @param series_Labels Series labels
     * @param info          Optional information strings
     */
    public HistogramChart(final Number[][] x_Data, String[] series_Labels, String[][] info) {
        m_xData = x_Data;
        m_seriesLabels = series_Labels;
        m_xAxisTitle = S_XTITLE;
        m_yAxisTitle = S_YTITLE;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        setFootnote(S_FOOTNOTE);
        if (info == null) {
            this.m_info = new String[series_Labels.length][10];
            for (int i = 0; i < series_Labels.length; i++) {
                Arrays.fill(this.m_info[i], "");
            }
        } else {
            this.m_info = info;
        }
    }

    /**
     * Constructor for Pie Chart
     * Chart title, width and height are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Data        Data values
     * @param series_Labels Series labels
     */
    public HistogramChart(final Number[][] x_Data, String[] series_Labels) {
        this(x_Data, series_Labels, null);

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
     * Sets chart series labels
     *
     * @param seriesLabels Chart series labels
     */
    public void setSeriesLabels(String[] seriesLabels) {
        m_seriesLabels = seriesLabels;
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
     * This function is used before generating chart string to check null and
     * inconsistent data
     *
     * @throws NullLabelException        Exception for null labels
     * @throws InconsistentDataException Exception for inconsistent data
     */
    private void checkData() throws NullLabelException, InconsistentDataException {

        if (m_xData == null) {
            throw new NullLabelException("X Data cannot be null!");
        }

        if (m_xData.length != m_seriesLabels.length) {
            throw new InconsistentDataException("Wrong number of Series Label or Data");
        }
    }

    /**
     * Generates Histogram Chart String based on template/plotly/histogramTemplate.html
     * Tokens in template file is replaced with corresponding value
     *
     * @return chart html string
     * @throws NullLabelException        if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();


        checkData();
        String info = "[";
        String data = "[";
        String series = "[";

        for (int i = 0; i < m_xData.length; i++) {
            data += "[";
            for (int j = 0; j < m_xData[i].length; j++) {
                data += "'" + m_xData[i][j] + "',";
            }
            data = data.substring(0, data.length() - 1) + "],";
        }
        data = data.substring(0, data.length() - 1) + "]";

        for (int i = 0; i < m_seriesLabels.length; i++) {
            series += "'" + m_seriesLabels[i] + "',";
        }
        series = series.substring(0, series.length() - 1) + "]";

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
        replaceMap.put("DATAX", data);
        replaceMap.put("SERIES", series);
        replaceMap.put("INFO", info);
        replaceMap.put("TITLE_X", m_xAxisTitle);
        replaceMap.put("TITLE_Y", m_yAxisTitle);
        replaceMap.put("WIDTH", String.valueOf(m_width));
        replaceMap.put("HEIGHT", String.valueOf(m_height));
        replaceMap.put("TITLE_CHART", m_title);
        replaceMap.put("FOOTNOTE", getFootnote());

        // Replace each map key in template file with map value
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            chartStr = chartStr.replaceAll("\\$" + e.getKey() + "\\$", e.getValue());
        }

        return chartStr;
    }

}

