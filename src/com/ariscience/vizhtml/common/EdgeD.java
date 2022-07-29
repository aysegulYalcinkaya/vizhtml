package com.ariscience.vizhtml.common;

import java.awt.Color;

/**
 * <pre>
 * Representation of simple Edge of a Directed Graph
 *
 * GraphD stands for Directed Graph (thus EdgeD stands for directed edge)
 * </pre>
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class EdgeD extends EdgeBase implements Comparable<EdgeD>{

    /**
     * the start vertex
     */
    private Vertex m_vertexStart = null;

    /**
     * the ending vertex
     */
    private Vertex m_vertexEnd = null;

    /*
     * Default Constructor for an Edge
     */
    public EdgeD() {
        this(null,null);
    }

    /*
     * Two Parameter Constructor for an edge
     *
     * @param vertexStart the initial vertex
     * @param vertexEnd the ending vertex
     */
    public EdgeD(Vertex vertexStart, Vertex vertexEnd) {
        this(vertexStart,vertexEnd,null);
    }

    /**
     * Constructor that specifies start, end vertex and preferred color
     *
     * @param vertexStart the initial vertex
     * @param vertexEnd the ending vertex
     * @param preferredColor the preferred edge color
     */
    public EdgeD(Vertex vertexStart, Vertex vertexEnd, Color preferredColor) {
        this(vertexStart,vertexEnd,preferredColor,null);
    }

    /**
     * Constructor that specifies start, end vertex, preferred color and informational string
     *
     * @param vertexStart the initial vertex
     * @param vertexEnd the ending vertex
     * @param preferredColor the preferred edge color
     * @param information informational string on the edge
     */
    public EdgeD(Vertex vertexStart, Vertex vertexEnd, Color preferredColor, String information) {
        m_vertexStart = vertexStart;
        m_vertexEnd = vertexEnd;
        m_preferredColor = preferredColor;
        m_information = information;
    }

    public Vertex getVertexStart() {
        return m_vertexStart;
    }

    public void setVertexStart(Vertex vertexStart) {
        m_vertexStart = vertexStart;
    }

    public Vertex getVertexEnd() {
        return m_vertexEnd;
    }

    public void setVertexEnd(Vertex vertexEnd) {
        m_vertexEnd = vertexEnd;
    }

    /**
     * Comparable API
     *
     * @param o other edge to compare to
     * @return -1,0,1 depending on edge ID
     */
    @Override
    public int compareTo(EdgeD o) {
        if(o==null){
            return -1;
        }
        return Long.compare(m_edgeID,o.m_edgeID);
    }

    /**
     * @return String representation
     */
    @Override
    public String toString() {
        return "EdgeD{" +
                "id=" + m_edgeID +
                ", vertexStart=" + m_vertexStart +
                ", vertexEnd=" + m_vertexEnd +
                ", preferredColor=" + m_preferredColor +
                ", information='" + m_information + '\'' +
                '}';
    }
}
