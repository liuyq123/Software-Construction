/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */

    @Test
    public void testToString() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.set("a", "b", 1);
        assertEquals("ab", graph.toString());
    }

    
    /*
     * Testing Vertex...
     */

    @Test
    public void testVertices() {
        Vertex<String> v1 = new Vertex<>("a");
        Vertex<String> v2 = new Vertex<>("b");
        v1.addInEdge("second", 1);
        v2.addOutEdge("first", 2);
        HashMap<String, Integer> result1 = new HashMap<>(), result2 = new HashMap<>();
        result1.put("second", 1);
        result2.put("first", 2);
        assertEquals(result1, v1.getInEdges());
        assertEquals(new HashMap<>(), v1.getOutEdges());
        assertEquals(new HashMap<>(), v2.getInEdges());
        assertEquals(result2, v2.getOutEdges());
    }
    
}
