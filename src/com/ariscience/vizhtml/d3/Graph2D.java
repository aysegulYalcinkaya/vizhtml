package com.ariscience.vizhtml.d3;

import com.ariscience.vizhtml.VizHTMLUtils;
import com.ariscience.vizhtml.common.EdgeD;
import com.ariscience.vizhtml.common.GraphD;
import com.ariscience.vizhtml.common.Vertex;
import com.ariscience.vizhtml.error.InconsistentDataException;
import com.ariscience.vizhtml.error.NullLabelException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;

/**
 * <pre>
 * 2D graph layout
 * </pre>
 *
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class Graph2D extends D3GraphBase{

    private GraphD m_graphD;

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
            InputStream inputStream = Graph2D.class.getClassLoader().getResourceAsStream("com/ariscience/vizhtml/template/d3/graph2D.html");

            // Read template file and close stream
            setTemplateString(VizHTMLUtils.readFullyAndClose(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate due to IOE:"+e.getMessage());
        }

    }

    /**
     * Constructor for Horizontal Bar Chart
     *
     * Chart title, subtitle, width, height, X axis title, Y axis title are set to default values
     *
     * Default values can be set through setter functions
     *
     * @param graphD the directed graph to render
     */
    public Graph2D(GraphD graphD) {
        m_graphD=graphD;
        m_title = S_TITLE;
        m_width = S_WIDTH;
        m_height = S_HEIGHT;
        setFootnote(S_FOOTNOTE);
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        m_title = title;
    }

    /**
     *
     * @param width
     */
    public void setWidth(int width) {
        m_width = width;
    }

    /**
     *
     * @param height
     */
    public void setHeight(int height) {
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


    }

    /**
     * Generates 2D Graph String based on template/d3/graph2D.html
     *
     * Tokens in template file is replaced with corresponding value
     *
     * @return chart html string
     * @throws NullLabelException if label not specified
     * @throws InconsistentDataException if data provided is inconsistent for chart type
     */
    public String generate() throws NullLabelException, InconsistentDataException {

        String chartStr = getTemplateString();

        checkData();

        String nodes = "[";
        String links = "[";

        NavigableSet<Vertex> vertices = m_graphD.getVertices();
        NavigableSet<EdgeD> edges = m_graphD.getEdges();

        //FEEDBACK: D3 library can automatically determine "optimal" vertex positions. So you have to handle that case where x and y coordinates of the vertex is NaN (i.e. signal to use D3's force based layout)
        for (Vertex vertex:vertices) {
            nodes += "{'x':" + vertex.getPreferredX() + ", 'y':" +vertex.getPreferredY()+"},";
        }

        nodes = nodes.substring(0, nodes.length() - 1)+"]";

        Object[] vertexArray =  vertices.toArray();
        for (EdgeD edge:edges) {
            int start = getVertexIndex(vertexArray,edge.getVertexStart());
            int end = getVertexIndex(vertexArray,edge.getVertexEnd());

            links += "{'target':" +start+ ", 'source':" +end +"},";
        }

        links = links.substring(0, links.length() - 1)+"]";

        // Map for replace tokens in template html file
        final Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("NODES", nodes);
        replaceMap.put("LINKS", links);
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

    private int getVertexIndex(Object[] vertexArray, Vertex vertexStart) {
        int index=0;
        for (Object o:vertexArray) {
            Vertex v=(Vertex) o;
            if (v.getPreferredX()==vertexStart.getPreferredX() && v.getPreferredY()==vertexStart.getPreferredY()){
                return index;
            }
            index++;
        }
        return -1;
    }


}
