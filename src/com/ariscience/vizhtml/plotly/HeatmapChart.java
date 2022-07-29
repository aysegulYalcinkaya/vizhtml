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
 * Heatmap Chart Class
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class HeatmapChart extends PChartBase {

    /**
     * Labels for X Axis
     */
    private String[][] m_xLabels;

    /**
     * Labels for Y Axis
     */
    private String[][] m_yLabels;

    /**
     * Char data
     */
    private Number[][] m_zData;
    /**
     * Optional information string for bar
     */
    private String[][] m_info;
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
        try{
            // Get template
            InputStream inputStream = PChartBase.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/plotly/heatmapTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        }
        catch (IOException e){
            throw new RuntimeException("Could not instantiate due to IOE:"+e.getMessage());
        }
    }
    /**
     * Constructor for Heatmap Chart
     * Chart title, width and height are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels Heatmap X Axis labels
     * @param y_Labels  Heatmap Y Axis labels
     * @param z_Data  Data values
     */
    public HeatmapChart(final String[][] x_Labels,final String[][] y_Labels, final Number[][] z_Data,String[][] info) {
        m_xLabels = x_Labels;
        m_yLabels = y_Labels;
        m_zData = z_Data;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        setFootnote(S_FOOTNOTE);
        if (info == null) {
            this.m_info = new String[m_yLabels[0].length][m_xLabels[0].length];
            for (int i=0;i<m_info.length;i++) {
                Arrays.fill(this.m_info[i], "");
            }
        } else {
            this.m_info = info;
        }
    }
    /**
     * Constructor for Heatmap Chart
     * Chart title, width and height are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels Heatmap X Axis labels
     * @param y_Labels  Heatmap Y Axis labels
     * @param z_Data  Data values
     */
    public HeatmapChart(final String[][] x_Labels,final String[][] y_Labels, final Number[][] z_Data) {
        this(x_Labels,y_Labels,z_Data,null);
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
        if (m_zData[0].length != m_xLabels[0].length) {
            throw new InconsistentDataException("Wrong number of X Label or Data");
        }
        if (m_zData.length != m_yLabels[0].length) {
            throw new InconsistentDataException("Wrong number of Y Label or Data");
        }

    }

    /**
     * Generates Heatmap Chart String based on template/plotly/heatmapTemplate.html
     * Tokens in template file is replaced with corresponding value
     *
     * @return chart html string
     * @throws NullLabelException if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();

        checkData();

        String columnsX = "[";
        String columnsY = "[";
        String data = "[";
        String info = "[";

        for (String name : m_xLabels[0]) {
            columnsX += "'" + name + "',";
        }

        columnsX = columnsX.substring(0, columnsX.length() - 1) + "]";

        for (String name : m_yLabels[0]) {
            columnsY += "'" + name + "',";
        }

        columnsY = columnsY.substring(0, columnsY.length() - 1) + "]";

        for(Number[] dataArray:m_zData){
            String zData="[";
            for (Number value:dataArray){
                zData+=value+",";
            }
            zData = zData.substring(0, zData.length() - 1) + "],";
            data+=zData;
        }
        data = data.substring(0, data.length() - 1) + "]";
        for (int j=0;j<m_info.length;j++) {
            info +="[";
            for (int i = 0; i < m_info[j].length; i++) {
                info += "'" + m_info[j][i] + "',";
            }
            info = info.substring(0, info.length() - 1) + "],";
        }
        info = info.substring(0, info.length() - 1) + "]";

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("DATAX", columnsX);
        replaceMap.put("DATAY", columnsY);
        replaceMap.put("DATAZ", data);
        replaceMap.put("INFO",info);
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

