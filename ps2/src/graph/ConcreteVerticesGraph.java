/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    @Override public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getVertex() == vertex)
                return false;
        }
        vertices.add(new Vertex(vertex));
        return true;
    }

    @Override public int set(String source, String target, int weight) {
        if (weight < 0)
            throw new RuntimeException("weight cannot be negative");

        if (source == target) {
            return 0;
        }

        add(source);
        Vertex sourceV = null;
        for (Vertex v : vertices) {
            if (v.getVertex() == source)
                sourceV = v;
        }

        add(target);
        Vertex targetV = null;
        for (Vertex v : vertices) {
            if (v.getVertex() == target)
                targetV = v;
        }

        int prevWeight;

        if (weight != 0) {
            sourceV.addOutEdge(target, weight);
            prevWeight = targetV.addInEdge(source, weight);
        } else {
            targetV.removeInEdge(source);
            prevWeight = sourceV.removeOutEdge(target);
        }

        return prevWeight;
    }

    @Override public boolean remove(String vertex) {
        Vertex v = null;
        for (Vertex ver : vertices) {
            if (ver.getVertex() == vertex)
                v = ver;
        }

        if (v == null)
            return false;

        Map<String, Integer> out = v.getOutEdges();
        Map<String, Integer> in = v.getInEdges();

        if (vertices.contains(v)) {
            for (Map.Entry<String, Integer> outEntry : out.entrySet()) {
                Vertex o = new Vertex(outEntry.getKey());
                o.getInEdges().remove(vertex);
            }
            for (Map.Entry<String, Integer> inEntry : in.entrySet()) {
                Vertex i = new Vertex(inEntry.getKey());
                i.getOutEdges().remove(vertex);
            }
        }

        return vertices.remove(v);
    }

    @Override public Set<String> vertices() {
        Set<String> set = new HashSet<>();
        for (Vertex v : vertices) {
            set.add(v.getVertex());
        }
        return set;
    }

    @Override public Map<String, Integer> sources(String target) {
        for (Vertex s : vertices) {
            if (s.getVertex() == target);
            return s.getInEdges();
        }
        return null;
    }

    @Override public Map<String, Integer> targets(String source) {
        for (Vertex s : vertices) {
            if (s.getVertex() == source);
            return s.getOutEdges();
        }
        return null;
    }



    // TODO toString()

}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {

    private final String vertex;
    private Map<String, Integer> inEdges = new HashMap<>();
    private Map<String, Integer> outEdges = new HashMap<>();

    Vertex(String vertex) {
        this.vertex = vertex;
    }

    public String getVertex() {
        return this.vertex;
    }

    public Map<String, Integer> getInEdges() {
        Map<String, Integer> sources = new HashMap<>();
        sources.putAll(inEdges);
        return sources;
    }

    public Map<String, Integer> getOutEdges() {
        Map<String, Integer> targets = new HashMap<>();
        targets.putAll(outEdges);
        return targets;
    }

    // return the weight of the removed edge, or 0 if not exist
    public int removeInEdge(String source) {
        if (!inEdges.containsKey(source)) {
            return 0;
        }

        int weight = inEdges.remove(source);
        return weight;
    }

    public int removeOutEdge(String target) {
        if (!outEdges.containsKey(target)) {
            return 0;
        }

        int weight = outEdges.remove(target);
        return weight;
    }

    // return prev weight of the edge
    public int addInEdge(String source, int weight) {
        int prevWeight;
        if (!inEdges.containsKey(source)) {
            prevWeight = 0;
        } else {
            prevWeight = inEdges.get(source);
            removeInEdge(source);
        }

        if (weight == 0) {
            return prevWeight;
        } else {
            inEdges.put(source, weight);
            return prevWeight;
        }
    }

    public int addOutEdge(String target, int weight) {
        int prevWeight;
        if (!outEdges.containsKey(target)) {
            prevWeight = 0;
        } else {
            prevWeight = outEdges.get(target);
            removeInEdge(target);
        }

        if (weight == 0) {
            return prevWeight;
        } else {
            outEdges.put(target, weight);
            return prevWeight;
        }
    }

    // TODO toString()

}
