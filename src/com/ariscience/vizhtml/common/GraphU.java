package com.ariscience.vizhtml.common;

import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * <pre>
 * Representation of very simple Undirected Graph
 *
 * GraphU stands for Undirected Graph
 * </pre>
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class GraphU {

    /**
     * set of vertices in this graph (threadsafe)
     */
    private final NavigableSet<Vertex> m_vertices = Collections.synchronizedNavigableSet(new TreeSet<>());

    /**
     * set of edges in this graph (threadsafe)
     */
    private final NavigableSet<EdgeU> m_edges = Collections.synchronizedNavigableSet(new TreeSet<>());

    /**
     * optional name of the graph
     */
    private String m_name = null;

    /**
     * Default Constructor that creates an empty Graph
     */
    public GraphU(String name) {
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
    public void addEdge(final EdgeU edge, boolean autoAddVertex) {
        if(edge==null){
            throw new IllegalArgumentException("edge cannot be null");
        }
        if(edge.getOneVertex()==null && edge.getOtherVertex()==null){
            throw new IllegalArgumentException("Edge must have atleast one vertex");
        }

        if(autoAddVertex){
            if(edge.getOneVertex()!=null){
                m_vertices.add(edge.getOneVertex());
            }
            if(edge.getOtherVertex()!=null){
                m_vertices.add(edge.getOtherVertex());
            }
        }

        //make sure vertices exist
        if(edge.getOneVertex()!=null){
            if(!m_vertices.contains(edge.getOneVertex())){
                throw new IllegalArgumentException("start vertex of edge not in graph:"+edge.getOneVertex());
            }
        }
        if(edge.getOtherVertex()!=null){
            if(!m_vertices.contains(edge.getOtherVertex())){
                throw new IllegalArgumentException("end vertex of edge not in graph"+edge.getOtherVertex());
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
    public NavigableSet<EdgeU> getEdges() {
        return m_edges;
    }

    /**
     * Returns all edges with specified vertex endpoints
     *
     * @param oneVertex one vertex to match (or null to match - which matches edge with one null vertex)
     * @param otherVertex other vertex to match (or null to match - which matches edge with one null vertex)
     * @return set of matching edges if any
     */
    public NavigableSet<EdgeU> getEdges(final Vertex oneVertex, final Vertex otherVertex) {
        if(oneVertex==null && otherVertex==null){
            throw new IllegalArgumentException("Both oneVertex and otherVertex CANNOT be null");
        }

        final TreeSet<EdgeU> returnval = new TreeSet<>();

        //there's one null vertex specified
        if(oneVertex==null || otherVertex==null){
            final Vertex notNullVertex = oneVertex==null?otherVertex:oneVertex;
            m_edges.forEach(edge->{
                if(notNullVertex.equals(edge.getOneVertex()) || notNullVertex.equals(edge.getOtherVertex())){
                    returnval.add(edge);
                }
            });
        }else{
            //both vertices specified
            m_edges.forEach(edge->{
                if(oneVertex.equals(edge.getOneVertex()) && otherVertex.equals(edge.getOtherVertex())){
                    returnval.add(edge);
                }else if(otherVertex.equals(edge.getOneVertex()) && oneVertex.equals(edge.getOtherVertex())){
                    returnval.add(edge);
                }
            });
        }


        return returnval;
    }

    /**
     * Gets all the edges of a given Vertex (whether its a start vertex match or end vertex match
     *
     * @param vertex a vertex to match (if null, matches edges with null start OR null end vertex)
     * @return all the edges that the vertex is connected to
     */
    public NavigableSet<EdgeU> getVertexEdges(final Vertex vertex){
        final TreeSet<EdgeU> returnval = new TreeSet<>();

        m_edges.forEach(edge->{
            if(vertex==null){
                if(edge.getOneVertex()==null || edge.getOtherVertex()==null) {
                    returnval.add(edge);
                }
            }else{
                if(vertex.equals(edge.getOneVertex()) || vertex.equals(edge.getOtherVertex())){
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
            return "GraphU{" +
                    "name='" + m_name + '\'' +
                    ", #Vertices=" + m_vertices.size() +
                    ", #Edges=" + m_edges.size() +
                    ", Vertices=" + m_vertices +
                    ", Edges=" + m_edges +
                    '}';
        }else {
            return "GraphU{" +
                    "name='" + m_name + '\'' +
                    ", #Vertices=" + m_vertices.size() +
                    ", #Edges=" + m_edges.size() +
                    '}';
        }
    }
    
}
