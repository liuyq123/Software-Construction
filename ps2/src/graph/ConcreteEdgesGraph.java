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
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    @Override public boolean add(L vertex) {
        return vertices.add(vertex);
    }

    @Override public int set(L source, L target, int weight) {
        if (weight < 0) {
            throw new RuntimeException("weight cannot be negative!");
        }

        int prevWeight = 0;
        for (Edge<L> e : edges) {
            if (e.target().equals(target) && e.source().equals(source)) {
                prevWeight = e.weight();
                edges.remove(e);
                break;
            }
        }

        if (weight > 0) {
            vertices.add(source);
            vertices.add(target);
            Edge<L> newEdge = new Edge(source, target, weight);
            edges.add(newEdge);
        }

        return prevWeight;
    }

    @Override public boolean remove(L vertex) {
        Iterator<Edge<L>> ei = edges.iterator();
        while (edges.iterator().hasNext()) {
            Edge<L> e = ei.next();

            if (e.source().equals(vertex) || e.target().equals(vertex)) {
                ei.remove();
            }
        }

        return vertices.remove(vertex);
    }

    @Override public Set<L> vertices() {
        Set<L> set = new HashSet<>();
        set.addAll(vertices);
        return set;
    }

    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (target.equals(edge.target()))
                sources.put(edge.source(), edge.weight());
        }
        return sources;
    }

    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (source.equals(edge.source()))
                targets.put(edge.target(), edge.weight());
        }
        return targets;
    }

    public String toString() {
        return ("(" + vertices.toString() + ", " +
                edges.toString() + ")").replace('[', '{').replace(']', '}');
    }

}

/**
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {

    private final L source, target;
    private final int weight;

    Edge (L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;

        checkRep();
    }

    private void checkRep() {
        assert weight > 0;
    }

    public L source() {
        return this.source;
    }

    public L target() {
        return this.target;
    }

    public int weight() {
        return this.weight;
    }

    public String toString() {
        return String.format("(%s, %s, %s)", this.source.toString(), this.target.toString(), this.weight);
    }

}
