package com.ariscience.vizhtml.common;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 * Representation of simple Vertex of a Directed Graph
 *
 * GraphD stands for Directed Graph (thus EdgeD stands for directed edge)
 * </pre>
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class Vertex implements Comparable<Vertex> {

    /**
     * simple sequential ID generator
     */
    private static AtomicLong s_sequentialIDGenerator = new AtomicLong(0);

    /**
     * NaN means no preference
     */
    private double m_preferredX = Double.NaN;

    /**
     * NaN means no preference
     */
    private double m_preferredY = Double.NaN;

    /**
     * NaN means no preference
     */
    private double m_preferredZ = Double.NaN;

    /**
     * By default set to 1d
     */
    private double m_preferredRadius = 1d;

    /**
     * Any informational string that goes along with this vertex
     */
    private String m_information;

    /**
     * if there's a color preference
     */
    private Color m_preferredColor = null;

    /**
     * auto assigned sequential ID
     */
    private final long m_vertexID = s_sequentialIDGenerator.getAndIncrement();

    /**
     * Creates a default empty vertex
     */
    public Vertex() {
        this(null);
    }

    /**
     * Create new vertex
     * @param information the information string to associate with this vertex
     */
    public Vertex(String information) {
        this(null,null);
    }

    /**
     * Create new vertex
     *
     * @param information the information string to associate with this vertex
     * @param preferredColor the preferred color of this vertex
     */
    public Vertex(String information, Color preferredColor) {
        this(Double.NaN,Double.NaN,Double.NaN,Double.NaN,information,preferredColor);
    }

    /**
     * Create new vertex
     *
     * @param preferredX the preferred X coordinate (if any). NaN if no preference.
     * @param preferredY the preferred Y coordinate (if any). NaN if no preference.
     * @param preferredZ the preferred Z coordinate (if any). NaN if no preference.
     * @param preferredRadius the preferred radius (if any). NaN if no preference.
     * @param information the information string to associate with this vertex
     * @param preferredColor the preferred color of this vertex
     */
    public Vertex(double preferredX, double preferredY, double preferredZ, double preferredRadius, String information, Color preferredColor) {
        m_preferredX = preferredX;
        m_preferredY = preferredY;
        m_preferredZ = preferredZ;
        m_preferredRadius = preferredRadius;
        m_information = information;
        m_preferredColor = preferredColor;
    }

    public double getPreferredX() {
        return m_preferredX;
    }

    public void setPreferredX(double preferredX) {
        m_preferredX = preferredX;
    }

    public double getPreferredY() {
        return m_preferredY;
    }

    public void setPreferredY(double preferredY) {
        m_preferredY = preferredY;
    }

    public double getPreferredZ() {
        return m_preferredZ;
    }

    public void setPreferredZ(double preferredZ) {
        m_preferredZ = preferredZ;
    }

    public double getPreferredRadius() {
        return m_preferredRadius;
    }

    public void setPreferredRadius(double preferredRadius) {
        if(preferredRadius<0){
            throw new IllegalArgumentException("Negative radius not allowed:"+preferredRadius);
        }
        if(Double.isInfinite(preferredRadius)){
            throw new IllegalArgumentException("Infinite radius not allowed:"+preferredRadius);
        }
        m_preferredRadius = preferredRadius;
    }

    /**
     * @return informational text, if any
     */
    public String getInformation() {
        return m_information;
    }

    /**
     * @param information informational text, if any
     */
    public void setInformation(String information) {
        m_information = information;
    }

    /**
     * @return preferred color (if any). null means use default color.
     */
    public Color getPreferredColor() {
        return m_preferredColor;
    }

    /**
     * @param preferredColor preferred color (if any). null means use default color.
     */
    public void setPreferredColor(Color preferredColor) {
        m_preferredColor = preferredColor;
    }

    /**
     * Comparable API
     *
     * @param o other edge to compare to
     * @return -1,0,1 depending on vertex ID
     */
    @Override
    public int compareTo(Vertex o) {
        if(o==null){
            return -1;
        }
        return Long.compare(m_vertexID,o.m_vertexID);
    }

    /**
     * @return string representation
     */
    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + m_vertexID +
                ", x=" + m_preferredX +
                ", y=" + m_preferredY +
                ", z=" + m_preferredZ +
                ", radius=" + m_preferredRadius +
                ", information='" + m_information + '\'' +
                ", Color=" + m_preferredColor +
                '}';
    }
}