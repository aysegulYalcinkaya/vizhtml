package com.ariscience.vizhtml.common;

import java.util.*;

/**
 * <pre>
 * Representation of very simple Directed Graph
 *
 * GraphD stands for Directed Graph
 * </pre>
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class GraphD {

    /**
     * set of vertices in this graph (threadsafe)
     */
    private final NavigableSet<Vertex> m_vertices = Collections.synchronizedNavigableSet(new TreeSet<>());

    /**
     * set of edges in this graph (threadsafe)
     */
    private final NavigableSet<EdgeD> m_edges = Collections.synchronizedNavigableSet(new TreeSet<>());

    /**
     * optional name of the graph
     */
    private String m_name = null;

    /**
     * Default Constructor that creates an empty Graph
     */
    public GraphD(String name) {
        m_name = name;
    }

    /**
     * Adds a vertex to the graph
     *
     * @param vertex a vertex to add
     */
    public void addVertex(final Vertex vertex) {
        if(vertex==null){
            throw new IllegalArgumentException("vertex cannot be null");
        }
        m_vertices.add(vertex);
    }

    /**
     * Adds specified edge to graph
     *
     * @param edge the edge to add
     */
    public void addEdge(final EdgeD edge, boolean autoAddVertex) {
        if(edge==null){
            throw new IllegalArgumentException("edge cannot be null");
        }
        if(edge.getVertexStart()==null && edge.getVertexEnd()==null){
            throw new IllegalArgumentException("Edge must have atleast one vertex");
        }

        if(autoAddVertex){
            if(edge.getVertexStart()!=null){
                m_vertices.add(edge.getVertexStart());
            }
            if(edge.getVertexEnd()!=null){
                m_vertices.add(edge.getVertexEnd());
            }
        }

        //make sure vertices exist
        if(edge.getVertexStart()!=null){
            if(!m_vertices.contains(edge.getVertexStart())){
                throw new IllegalArgumentException("start vertex of edge not in graph:"+edge.getVertexStart());
            }
        }
        if(edge.getVertexEnd()!=null){
            if(!m_vertices.contains(edge.getVertexEnd())){
                throw new IllegalArgumentException("end vertex of edge not in graph"+edge.getVertexEnd());
            }
        }

        //all good to add
        m_edges.add(edge);
    }

    /**
     * @return the vertices in this graph (NOTE this is the underlying data structure - use carefully)
     */
    public NavigableSet<Vertex> getVertices() {
        return m_vertices;
    }

    /**
     * @return the edges in this graph (NOTE this is the underlying data structure - use carefully)
     */
    public NavigableSet<EdgeD> getEdges() {
        return m_edges;
    }

    /**
     * Returns all edges with specified start and end vertex
     *
     * @param startVertex the start vertex to match (or null to match - which matches edge with a null start vertex)
     * @param endVertex the end vertex to match (or null to match - which matches edge with a null end vertex)
     * @return set of matching edges if any
     */
    public NavigableSet<EdgeD> getEdges(final Vertex startVertex, final Vertex endVertex) {
        if(startVertex==null && endVertex==null){
            throw new IllegalArgumentException("Both start and end vertices CANNOT be null");
        }

        final TreeSet<EdgeD> returnval = new TreeSet<>();

        m_edges.forEach(edge->{
            boolean skip = false;
            if((startVertex==null && edge.getVertexStart()!=null) || (startVertex!=null && edge.getVertexStart()==null) ){
               //no match
               skip = true;
            }else if((endVertex==null && edge.getVertexEnd()!=null) || (endVertex!=null && edge.getVertexEnd()==null)){
               //no match
               skip = true;
            }

            if(skip){
                //do nothing
            }else {
                boolean startMatches = false;
                if ((startVertex == null && edge.getVertexStart() == null) || startVertex.equals(edge.getVertexStart())) {
                    startMatches = true;
                }

                boolean endMatches = false;
                if ((endVertex == null && edge.getVertexEnd() == null) || endVertex.equals(edge.getVertexEnd())) {
                    endMatches = true;
                }

                if(startMatches && endMatches){
                    returnval.add(edge);
                }
            }
        });

        return returnval;
    }

    /**
     * Gets all the edges of a given Vertex (whether its a start vertex match or end vertex match
     *
     * @param vertex a vertex to match (if null, matches edges with null start OR null end vertex)
     * @return all the edges that the vertex is connected to
     */
    public NavigableSet<EdgeD> getVertexEdges(final Vertex vertex){
        final TreeSet<EdgeD> returnval = new TreeSet<>();

        m_edges.forEach(edge->{
            if(vertex==null){
                if(edge.getVertexStart()==null || edge.getVertexEnd()==null) {
                    returnval.add(edge);
                }
            }else{
                if(vertex.equals(edge.getVertexStart()) || vertex.equals(edge.getVertexEnd())){
                    returnval.add(edge);
                }
            }
        });

        return returnval;
    }

    /**
     * Gets all the edges of a given Vertex (if its a start vertex match)
     *
     * @param vertex a vertex to match (if null, matches edges with null start vertex)
     * @return all the edges that the vertex is connected to as end vertex
     */
    public NavigableSet<EdgeD> getVertexEdgesMatchStart(final Vertex vertex){
        final TreeSet<EdgeD> returnval = new TreeSet<>();

        m_edges.forEach(edge->{
            if(vertex==null){
                if(edge.getVertexStart()==null) {
                    returnval.add(edge);
                }
            }else{
                if(vertex.equals(edge.getVertexStart())){
                    returnval.add(edge);
                }
            }
        });

        return returnval;
    }

    /**
     * Gets all the edges of a given Vertex (if its a end vertex match)
     *
     * @param vertex a vertex to match (if null, matches edges with null end vertex)
     * @return the edges that the vertex is connected to as start vertex
     */
    public NavigableSet<EdgeD> getVertexEdgesMatchEnd(final Vertex vertex){
        final TreeSet<EdgeD> returnval = new TreeSet<>();

        m_edges.forEach(edge->{
            if(vertex==null){
                if(edge.getVertexEnd()==null) {
                    returnval.add(edge);
                }
            }else{
                if(vertex.equals(edge.getVertexEnd())){
                    returnval.add(edge);
                }
            }
        });

        return returnval;
    }

    /**
     * Delegates to {@link #toString(boolean)} with details set to false
     *
     * @return simple string representation
     */
    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * @return simple string representation
     */
    public String toString(final boolean printDetails) {
        if(printDetails){
            return "GraphD{" +
                    "name='" + m_name + '\'' +
                    ", #Vertices=" + m_vertices.size() +
                    ", #Edges=" + m_edges.size() +
                    ", Vertices=" + m_vertices +
                    ", Edges=" + m_edges +
                    '}';
        }else {
            return "GraphD{" +
                    "name='" + m_name + '\'' +
                    ", #Vertices=" + m_vertices.size() +
                    ", #Edges=" + m_edges.size() +
                    '}';
        }
    }
}

