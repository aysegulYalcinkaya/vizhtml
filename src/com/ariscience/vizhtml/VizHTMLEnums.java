package com.ariscience.vizhtml;

/**
 * Encapsulates common enums including but not limited to chart types
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class VizHTMLEnums {

    /**
     * Simple encapsulation of chart types supported by the HTML generation
     */
    public enum ChartType{

        //FEEDBACK - remember to register the supported chart types here....

        Graph_2DF(100,"Graph Visualization in 2D using Force based placement"),
        Graph_3DF(200,"Graph Visualization in 3D using Force based placement"),
        Graph_3DF_SM(300,"Graph Visualization in 3D - Animated/Slow Motion"),
        Bar_Horizontal(400,"Bar Graph - Horizontal"),
        Bar_Vertical(500,"Bar Graph - Vertical"),
        Line(600,"Line chart"),
        Pie(700,"Pie chart"),
        Scatter(800,"Scatter Plot chart"),
        Bubble(900,"Bubble chart"),
        Histogram(1000,"Histogram chart");

        /**
         * the value of the enum (may or may not correspond to ordinal value)
         */
        private final int m_value;

        /**
         * the description of the enum
         */
        private final String m_description;

        /**
         * Internal constructor
         *
         * @param value a value associate with the enum (not necessarily ordinal value)
         * @param description description of the enum
         */
        private ChartType(final int value, final String description)
        {
            m_description = description;
            m_value = value;
        }

        /**
         * @return the enum description
         */
        public String getDescription() {
            return m_description;
        }
    }

    /**
     * <pre>
     * Simple encapsulation of tokens
     *
     * Remember the tokens are like $DATAX$, $DATAY$ and so on in the template files
     * </pre>
     */
    public enum ReplacementToken{

        //FEEDBACK: Add in a DATAZ (i.e. since we have X and Y, might as well have a Z)

        TITLE_PAGE(100,"Page Title","The title of the HTML page that is generated"),
        TITLE_CHART(200,"Chart Title","The title of the chart that is generated"),
        DATAX(300,"X Data","E.g. for Pie charts, Categorical (labels and corresponding value) bar charts"),
        FOOTNOTE(500,"Footnote","Footnote that is included at the bottom"),
        DATAY(600,"Y Data","E.g. for Pie charts, Categorical (labels and corresponding value) bar charts"),
        DATA(700,"Data","E.g. for bar charts in key,value pairs"), //FEEDBACK in the description field provide an example
        COLUMNS(800,"Chart Columns","E.g. bar charts"), //FEEDBACK in the description field provide an example
        SUBTITLE(900,"Chart Subtitle","Subtitle of the chart that is generated"),
        WIDTH(1000,"Chart Width (px)","Width of the chart"),
        HEIGHT(1100,"Chart Height (px)","Height of the chart"),
        TITLE_X(1200,"X Axis Title",""),
        TITLE_Y(1300,"Y Axis Title",""),
        TITLE_Z(1400,"Z Axis Title",""),
        BUBBLE_TEXT(1500,"Bubble Text for Plotly Bubble Chart",""),
        NODES(1600,"Nodes for D3 Graph",""),
        LINKS(1400,"Links for D3 Graph","");

        /**
         * the value of the enum (may or may not correspond to ordinal value)
         */
        private final int m_value;

        /**
         * the description of the enum
         */
        private final String m_description;

        /**
         * the context of the token
         */
        private final String m_context;

        /**
         * Internal constructor
         *
         * @param value a value associate with the enum (not necessarily ordinal value)
         * @param description description of the enum
         * @param context a user readable explanation context
         */
        private ReplacementToken(final int value, final String description, final String context)
        {
            m_description = description;
            m_value = value;
            m_context = context;
        }

        /**
         * @return the enum description
         */
        public String getDescription() {
            return m_description;
        }
    }

}
