/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAdd() {
        Graph<String> emptyInstance = emptyInstance();

        assertEquals(true, emptyInstance.add("a"));
        assertEquals(false, emptyInstance.add("a"));
        assertEquals(true, emptyInstance.add("b"));
        assertEquals(true, emptyInstance.add("cd"));
    }

    @Test
    public void testSet() {
        Graph<String> emptyInstance = emptyInstance();

        emptyInstance.add("a");
        emptyInstance.add("b");
        emptyInstance.add("c");

        assertEquals(0, emptyInstance.set("a", "b", 1));
        assertEquals(1, emptyInstance.set("a", "b", 2));

        assertEquals(0, emptyInstance.set("c", "c", 1));
        assertEquals(0, emptyInstance.set("c", "c", 1));

        assertEquals(Collections.EMPTY_MAP, emptyInstance.sources("c"));
        assertEquals(Collections.EMPTY_MAP, emptyInstance.targets("c"));
    }

    @Test
    public void testRemove() {
        Graph<String> emptyInstance = emptyInstance();

        emptyInstance.add("a");
        emptyInstance.add("b");

        assertEquals(true, emptyInstance.remove("b"));
        assertEquals(false, emptyInstance.remove("b"));
    }

    @Test
    public void testVertices() {
        Graph<String> emptyInstance = emptyInstance();

        emptyInstance.add("a");
        emptyInstance.add("b");
        emptyInstance.add("c");

        Set<String> vertices = new HashSet<String>();
        vertices.add("a");
        vertices.add("b");
        vertices.add("c");

        assertEquals(vertices, emptyInstance.vertices());
    }

    @Test
    public void testSourceTarget() {
        Graph<String> emptyInstance = emptyInstance();

        emptyInstance.add("a");
        emptyInstance.add("b");
        emptyInstance.add("c");
        emptyInstance.add("d");

        emptyInstance.set("a", "b", 1);
        emptyInstance.set("a", "c", 3);
        emptyInstance.set("a", "d", 5);
        emptyInstance.set("b", "b", 1);
        emptyInstance.set("b", "b", 1);
        emptyInstance.set("b", "c", 2);
        emptyInstance.set("d", "a", 4);

        Map<String, Integer> sources = new HashMap<>();
        sources.put("d", 4);
        assertEquals(sources, emptyInstance.sources("a"));
        sources.remove("d");
        sources.put("a", 3);
        sources.put("b", 2);
        assertEquals(sources, emptyInstance.sources("c"));
        sources.clear();
        sources.put("a", 1);
        assertEquals(sources, emptyInstance.sources("b"));
        assertEquals(Collections.EMPTY_MAP, emptyInstance.sources("e"));

        Map<String, Integer> targets = new HashMap<>();
        targets.put("b", 1);
        targets.put("c", 3);
        targets.put("d", 5);
        assertEquals(targets, emptyInstance.targets("a"));
        assertEquals(Collections.EMPTY_MAP, emptyInstance.targets("c"));
        targets.clear();
        targets.put("a", 4);
        assertEquals(targets, emptyInstance.targets("d"));
    }

}
