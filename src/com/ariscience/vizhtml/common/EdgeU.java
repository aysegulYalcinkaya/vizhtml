package com.ariscience.vizhtml.common;

import java.awt.*;

/**
 * <pre>
 * Representation of simple Edge of a Undirected Graph
 *
 * GraphU stands for Undirected Graph (thus EdgeU stands for undirected edge)
 * </pre>
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class EdgeU extends EdgeBase implements Comparable<EdgeU> {

    /**
     * one vertex of undirected edge
     */
    private Vertex m_oneVertex = null;

    /**
     * another vertex of undirected edge
     */
    private Vertex m_otherVertex = null;

    /*
     * Default Constructor for an undirected edge with two null vertices
     */
    public EdgeU() {
        this(null,null);
    }

    /*
     * Constructor that two vertices of the undirected edge
     *
     * @param oneVertex the initial vertex
     * @param otherVertex the ending vertex
     */
    public EdgeU(Vertex oneVertex, Vertex otherVertex) {
        this(oneVertex, otherVertex,null);
    }

    /**
     * Constructor that two vertices of the undirected edge and a preferred color
     *
     * @param oneVertex the initial vertex
     * @param otherVertex the ending vertex
     * @param preferredColor the preferred edge color
     */
    public EdgeU(Vertex oneVertex, Vertex otherVertex, Color preferredColor) {
        this(oneVertex, otherVertex,preferredColor,null);
    }

    /**
     * Constructor that specifies two vertices of the undirected edge, preferred color and informational string
     *
     * @param oneVertex the initial vertex
     * @param otherVertex the ending vertex
     * @param preferredColor the preferred edge color
     * @param information informational string on the edge
     */
    public EdgeU(Vertex oneVertex, Vertex otherVertex, Color preferredColor, String information) {
        m_oneVertex = oneVertex;
        m_otherVertex = otherVertex;
        m_preferredColor = preferredColor;
        m_information = information;
    }

    public Vertex getOneVertex() {
        return m_oneVertex;
    }

    public void setOneVertex(Vertex oneVertex) {
        m_oneVertex = oneVertex;
    }

    public Vertex getOtherVertex() {
        return m_otherVertex;
    }

    public void setOtherVertex(Vertex otherVertex) {
        m_otherVertex = otherVertex;
    }

    /**
     * Comparable API
     *
     * @param o other edge to compare to
     * @return -1,0,1 depending on edge ID
     */
    @Override
    public int compareTo(EdgeU o) {
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
        return "EdgeU{" +
                "id=" + m_edgeID +
                ", vertexStart=" + m_oneVertex +
                ", vertexEnd=" + m_otherVertex +
                ", preferredColor=" + m_preferredColor +
                ", information='" + m_information + '\'' +
                '}';
    }
}
