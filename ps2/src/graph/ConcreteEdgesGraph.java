/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    
    // checkRep
    private void checkRep() {
        for (String vertex : vertices)
            assert (vertex != null);
        for (Edge edge : edges)
            assert (edge != null);
    }
    
    @Override public boolean add(String vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(String source, String target, int weight) {
        if (weight < 0) {
            throw new RuntimeException("weight cannot be negative!");
        }

        int prevWeight = sources(target).get(source);
        vertices.add(source);
        vertices.add(target);
        Edge oldEdge = new Edge(source, target, prevWeight);
        Edge newEdge = new Edge(source, target, weight);
        edges.remove(oldEdge);
        if (weight != 0)
            edges.add(newEdge);
        return prevWeight;
    }
    
    @Override public boolean remove(String vertex) {
        if (vertices.contains(vertex)) {
            Map<String, Integer> sources = sources(vertex);
            for (Map.Entry<String, Integer> source : sources.entrySet()) {
                Edge edge = new Edge(source.getKey(), vertex, source.getValue());
                edges.remove(edge);
            }

            Map<String, Integer> targets = targets((vertex));
            for (Map.Entry<String, Integer> target : targets.entrySet()) {
                Edge edge = new Edge(vertex, target.getKey(), target.getValue());
                edges.remove(edge);
            }

            return vertices.remove(vertex);
        } else {
            return false;
        }

    }
    
    @Override public Set<String> vertices() {
        return this.vertices;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.target() == target)
                sources.put(edge.source(), edge.weight());
        }
        return sources;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.source() == source)
                targets.put(edge.target(), edge.weight());
        }
        return targets;
    }
    
    // TODO toString()
    
}

/**
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {

    private final String source, target;
    private final int weight;
    
    Edge (String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    private void checkRep() {
        assert (weight > 0 && !source.equals(target));
    }
    
    public String source() {
        return this.source;
    }

    public String target() {
        return this.target;
    }

    public int weight() {
        return this.weight;
    }


    
    // TODO toString()
    
}
