package com.ariscience.vizhtml.common;

import java.awt.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base edge class the EdgeD and EdgeU should extend
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public abstract class EdgeBase {

    /**
     * simple sequential ID generator
     */
    private static AtomicLong s_sequentialIDGenerator = new AtomicLong(0);

    /**
     * Null means no preferred color
     */
    protected Color m_preferredColor = null;

    /**
     * weight of the edge (by default set to 1d)
     */
    protected double m_weight = 1d;

    /**
     * Any informational string that goes along with this vertex
     */
    protected String m_information;

    /**
     * auto assigned sequential ID
     */
    protected final long m_edgeID = s_sequentialIDGenerator.getAndIncrement();

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
     * @return the edge weight
     */
    public double getWeight() {
        return m_weight;
    }

    /**
     * @param weight set the edge weight (must be finite)
     */
    public void setWeight(final double weight) {
        if(!Double.isFinite(weight)){
            throw new IllegalArgumentException("Edge weight must be finite (remember default is 1d):"+weight);
        }
        m_weight = weight;
    }

}
