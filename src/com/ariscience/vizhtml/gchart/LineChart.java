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
 * Line Chart Class
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class LineChart extends GChartBase {

    /**
     * Labels on X Axis
     */
    private String[][] m_xLabels;

    /**
     * Data for corresponding X labels
     * Series1  m_yData[0][]
     * Series2 m_yData[1][]
     */
    private Number[][] m_yData;

    /**
     * Series labels for multi series charts
     */
    private String[] m_seriesLabels;

    private String[][] m_info;

    /**
     * X Axis Label
     */
    private String m_xAxisTitle;

    /**
     * Y Axis Label
     */
    private String m_yAxisTitle;

    /**
     * Chart title
     */
    private String m_title;

    /**
     * Chart subtitle
     */
    private String m_subtitle;

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
            InputStream inputStream = LineChart.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/gchart/lineTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:" + e.getMessage());
        }
    }

    /**
     * Constructor for Line Chart
     * Chart title, subtitle, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels      Label values on X Axis
     * @param y_Data        Values for X Axis Labels
     * @param series_Labels Series labels
     * @param info          Optional information strings [numberOfSeries][numberOfXLabels]
     */
    public LineChart(final String[][] x_Labels, final Number[][] y_Data, String[] series_Labels, String[][] info) {
        m_xLabels = x_Labels;
        m_yData = y_Data;
        m_seriesLabels = series_Labels;
        m_title = S_TITLE;
        m_subtitle = S_SUBTITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        m_xAxisTitle = S_XTITLE;
        m_yAxisTitle = S_YTITLE;
        setFootnote(S_FOOTNOTE);
        if (info == null){
            this.m_info=new String[m_seriesLabels.length][];
            Arrays.fill(this.m_info, null);
        }
    }

    /**
     * Constructor for Line Chart
     * Chart title, subtitle, width, height, X axis title, Y axis title are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels      Label values on X Axis
     * @param y_Data        Values for X Axis Labels
     * @param series_Labels Series labels
     */
    public LineChart(final String[][] x_Labels, final Number[][] y_Data, String[] series_Labels) {
        this(x_Labels,y_Data,series_Labels,null);
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
     * Sets chart series labels
     *
     * @param seriesLabels Chart series labels
     */
    public void setSeriesLabels(String[] seriesLabels) {
        m_seriesLabels = seriesLabels;
    }

    public void setInfo(String[][] info) {
        m_info = info;
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
     * Sets Chart Subtitle
     *
     * @param subtitle Chart subtitle
     */
    public void setSubtitle(String subtitle) {
        m_subtitle = subtitle;
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
     * This function is used before generating chart string to check null and
     * inconsistent data
     *
     * @throws NullLabelException        exception for null label
     * @throws InconsistentDataException exception for inconsistent data
     */
    private void checkData() throws NullLabelException, InconsistentDataException {
        if (m_xLabels == null) {
            throw new NullLabelException("X Label cannot be null!");
        }

        if (m_yData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }

        if (m_yData.length != m_seriesLabels.length) {
            throw new InconsistentDataException("Wrong number of Series Label or Data");
        }

        if (m_info != null && m_info.length != m_seriesLabels.length) {
            throw new InconsistentDataException("Wrong number of Series Label or information data");
        }

        for (int i = 0; i < m_info.length; i++) {
            if (m_info[i] != null && m_info[i].length != m_xLabels[0].length) {
                throw new InconsistentDataException("Wrong number of xAxis Labels or Information String");
            }
        }

        for (int i = 0; i < m_yData.length; i++) {
            if (m_yData[i].length != m_xLabels[0].length) {
                throw new InconsistentDataException("Wrong number of xAxis Labels or Data");
            }
        }
    }

    /**
     * Generates Line Chart String based on template/gchart/lineTemplate.html
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

        String series = "";
        String data = "";

        int index = 0;
        for (String serie : m_seriesLabels) {
            series += "data.addColumn('number', '" + serie + "');" + System.lineSeparator();
            // if (m_info[index]!=null){
            series += "data.addColumn({type:'string',role:'tooltip'});" + System.lineSeparator();
            //}
            index++;
        }

        for (int i = 0; i < m_xLabels[0].length; i++) {
            data += "['" + m_xLabels[0][i] + "',";

            for (int j = 0; j < m_yData.length; j++) {
                data += m_yData[j][i] + ",";
                if (m_info[j] != null && m_info[j][i] != null) {
                    data += "'" + m_xLabels[0][i] + "\\\\n" + m_seriesLabels[j] + " : " + m_yData[j][i] + "\\\\n" + m_info[j][i] + "',";
                } else {
                    data += "'" + m_xLabels[0][i] + "\\\\n" + m_seriesLabels[j] + " : " + m_yData[j][i] + "',";
                }
            }
            data = data.substring(0, data.length() - 1) + "],";
        }

        data = data.substring(0, data.length() - 1);

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("TITLE_X", m_xAxisTitle);
        replaceMap.put("SERIESLABELS", series);
        replaceMap.put("DATA", data);
        replaceMap.put("WIDTH", String.valueOf(m_width));
        replaceMap.put("HEIGHT", String.valueOf(m_height));
        replaceMap.put("TITLE_CHART", m_title);
        replaceMap.put("SUBTITLE", m_subtitle);
        replaceMap.put("TITLE_Y", m_yAxisTitle);
        replaceMap.put("FOOTNOTE", getFootnote());

        // Replace each map key in template file with map value
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            chartStr = chartStr.replaceAll("\\$" + e.getKey() + "\\$", e.getValue());
        }

        return chartStr;
    }

}

