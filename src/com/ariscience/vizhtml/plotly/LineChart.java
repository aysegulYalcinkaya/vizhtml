package com.ariscience.vizhtml.plotly;

import com.ariscience.vizhtml.VizHTMLUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Line Chart Class
 * <p>
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 * </p>
 */
public class LineChart extends LineScatterChartBase {

    static {
        try {
            // Get template
            InputStream inputStream = LineScatterChartBase.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/plotly/lineScatterTemplate.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:"+e.getMessage());
        }
    }

    /**
     * Constructor for Line Chart
     * Chart type is set to "lines"
     * @param x_Data    Values for X Axis
     * @param y_Data    Values for Y Axis
     */
    public LineChart(final Number[][] x_Data, final Number[][] y_Data) {
        super(x_Data,y_Data);
        this.setType("lines");
    }

}

