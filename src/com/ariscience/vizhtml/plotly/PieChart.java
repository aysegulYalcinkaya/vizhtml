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
 * Pie Chart Class
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class PieChart extends PChartBase {

    /**
     * Labels for data
     */
    private String[][] m_xLabels;
    /**
     * Chart data
     */
    private Number[][] m_yData;

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
            InputStream inputStream = PChartBase.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/plotly/pieTemplate.html");

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
     * @param y_Data   Data values
     */
    public PieChart(final String[][] x_Labels, final Number[][] y_Data,String[][] info) {
        m_xLabels = x_Labels;
        m_yData = y_Data;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        setFootnote(S_FOOTNOTE);
        if (info == null) {
            this.m_info = new String[1][m_yData[0].length];
            Arrays.fill(this.m_info[0], "");
        } else {
            this.m_info = info;
        }
    }
    /**
     * Constructor for Pie Chart
     * Chart title, width and height are set to default values
     * Default values can be set through setter functions
     *
     * @param x_Labels Pie data info labels
     * @param y_Data   Data values
     */
    public PieChart(final String[][] x_Labels, final Number[][] y_Data) {
       this(x_Labels,y_Data,null);

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

        if (m_yData == null) {
            throw new NullLabelException("Y Data cannot be null!");
        }
        if (m_yData[0].length != m_xLabels[0].length) {
            throw new InconsistentDataException("Wrong number of X Label or Y Data");
        }

    }

    /**
     * Generates Pie Chart String based on template/plotly/pieTemplate.html
     * Tokens in template file is replaced with corresponding value
     *
     * @return chart html string
     * @throws NullLabelException if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();


        checkData();

        String columns = "[";
        String data = "[";
        String info = "[";

        for (String name : m_xLabels[0]) {
            columns += "'" + name + "',";
        }

        columns = columns.substring(0, columns.length() - 1) + "]";

        for (int i = 0; i < m_xLabels[0].length; i++) {
            data += m_yData[0][i] + ", ";
        }

        data = data.substring(0, data.length() - 2) + "]";

        for (int i = 0; i < m_info[0].length; i++) {
            info += "'"+m_info[0][i] + "',";
        }
        info = info.substring(0, info.length() - 1) + "]";

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("DATAX", columns);
        replaceMap.put("DATAY", data);
        replaceMap.put("INFO", info);
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

