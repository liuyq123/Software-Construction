/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testEmptyInput() throws IOException {
        File corpus = new File("src/poet/mugar-omni-theater.txt");
        GraphPoet graph = new GraphPoet(corpus);
        String input = "";
        String output = graph.poem(input);
        assertEquals(input, output);
    }

    @Test
    public void testPoem() throws IOException {
        File corpus = new File("src/poet/mugar-omni-theater.txt");
        GraphPoet graph = new GraphPoet(corpus);
        String input = "Test the Theater system.";
        String output = graph.poem(input);
        String expected = "test of the theater sound system.";
        assertEquals(expected, output);
    }
}
