/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;


import java.util.*;

/**
 * An implementation of Graph.
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    @Override public boolean add(L vertex) {
        for (Vertex<L> v : vertices) {
            if (v.getVertex().equals(vertex))
                return false;
        }
        vertices.add(new Vertex<L>(vertex));
        return true;
    }

    @Override public int set(L source, L target, int weight) {
        if (weight < 0)
            throw new RuntimeException("weight cannot be negative");

        add(source);
        Vertex<L> sourceV = null;
        for (Vertex<L> v : vertices) {
            if (v.getVertex().equals(source)) {
                sourceV = v;
                break;
            }
        }

        add(target);
        Vertex<L> targetV = null;
        for (Vertex<L> v : vertices) {
            if (v.getVertex().equals(target)) {
                targetV = v;
                break;
            }
        }

        int prevWeight = 0;

        for (Vertex<L> outEntry : sourceV.getOutEdges().keySet()) {
            if (outEntry.equals(targetV)) {
                prevWeight = sourceV.setEdge(targetV, weight);
            }
        }

        if (prevWeight == 0)
            sourceV.setEdge(targetV, weight);

        return prevWeight;
    }

    @Override public boolean remove(L vertex) {
        Vertex<L> v = null;
        for (Vertex<L> ver : vertices) {
            if (ver.getVertex().equals(vertex))
                v = ver;
        }

        if (v == null)
            return false;

        Map<Vertex<L>, Integer> out = v.getOutEdges();
        Map<Vertex<L>, Integer> in = v.getInEdges();


        for (Map.Entry<Vertex<L>, Integer> outEntry : out.entrySet()) {
            v.setEdge(outEntry.getKey(), 0);
        }
        for (Map.Entry<Vertex<L>, Integer> inEntry : in.entrySet()) {
            inEntry.getKey().setEdge(v, 0);
        }

        return vertices.remove(v);
    }

    @Override public Set<L> vertices() {
        final Set<L> set = new HashSet<>();
        for (Vertex<L> v : vertices) {
            set.add(v.getVertex());
        }
        return set;
    }

    @Override public Map<L, Integer> sources(L target) {
        final Map<L, Integer> map = new HashMap<>();

        for (Vertex<L> t : vertices) {
            if (t.getVertex().equals(target))
                for (Map.Entry<Vertex<L>, Integer> entry : t.getInEdges().entrySet()) {
                    map.put(entry.getKey().getVertex(), entry.getValue());
                }
        }

        return map;
    }

    @Override public Map<L, Integer> targets(L source) {
        final Map<L, Integer> map = new HashMap<>();

        for (Vertex<L> s : vertices) {
            if (s.getVertex().equals(source))
                for (Map.Entry<Vertex<L>, Integer> entry : s.getOutEdges().entrySet()) {
                    map.put(entry.getKey().getVertex(), entry.getValue());
                }
        }

        return map;
    }



    public String toString() {
        String s = "";
        for (Vertex v : vertices) {
            s += v.toString();
        }
        return s;
    }

}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {

    private final L vertex;
    private final Map<Vertex<L>, Integer> inEdges = new HashMap<>();
    private final Map<Vertex<L>, Integer> outEdges = new HashMap<>();

    Vertex(L vertex) {
        this.vertex = vertex;
    }

    public L getVertex() {
        return this.vertex;
    }

    public Map<Vertex<L>, Integer> getInEdges() {
        Map<Vertex<L>, Integer> sources = new HashMap<>();
        sources.putAll(inEdges);
        return sources;
    }

    public Map<Vertex<L>, Integer> getOutEdges() {
        Map<Vertex<L>, Integer> targets = new HashMap<>();
        targets.putAll(outEdges);
        return targets;
    }

    public int setEdge(Vertex<L> v, int weight) {
        final int prevWeight = outEdges.getOrDefault(v, 0);
        if (weight > 0) {
            outEdges.put(v, weight);
            v.inEdges.put(this, weight);
        } else {
            outEdges.remove(v, weight);
            v.inEdges.remove(this, weight);
        }
        return prevWeight;
    }

    public String toString() {
        return getVertex().toString();
    }

}
