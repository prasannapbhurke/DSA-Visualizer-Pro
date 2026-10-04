package com.dsavisualizer.app.ui.visualization.runtime;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HuffmanTreeModelTest {

    @Test
    public void testCanonicalClrsTree() {
        int[] clrs = {5, 9, 12, 13, 16, 45};
        HuffmanTreeModel model = HuffmanTreeModel.build(clrs);

        assertNotNull(model.getRoot());
        assertEquals(100, model.getRoot().weight);
        assertEquals(6, model.getLeaves().size());
        assertEquals(11, model.getAllNodes().size());

        // Verify prefix codes
        List<HuffmanTreeModel.CodeEntry> codes = model.getCodesList();
        assertEquals(6, codes.size());

        // Find each character's code
        String codeA = "", codeB = "", codeC = "", codeD = "", codeE = "", codeF = "";
        for (HuffmanTreeModel.CodeEntry entry : codes) {
            if ("a".equals(entry.character)) codeA = entry.code;
            if ("b".equals(entry.character)) codeB = entry.code;
            if ("c".equals(entry.character)) codeC = entry.code;
            if ("d".equals(entry.character)) codeD = entry.code;
            if ("e".equals(entry.character)) codeE = entry.code;
            if ("f".equals(entry.character)) codeF = entry.code;
        }

        assertEquals("0", codeA);
        assertEquals("100", codeC);
        assertEquals("101", codeB);
        assertEquals("111", codeD);
        assertEquals("1100", codeF);
        assertEquals("1101", codeE);

        // Verify prefix-free property
        for (int i = 0; i < codes.size(); i++) {
            for (int j = 0; j < codes.size(); j++) {
                if (i != j) {
                    assertFalse(codes.get(i).code.startsWith(codes.get(j).code));
                }
            }
        }
    }

    @Test
    public void testFibonacciSkewTree() {
        int[] fib = {1, 2, 3, 5, 8, 13, 21};
        HuffmanTreeModel model = HuffmanTreeModel.build(fib);

        assertNotNull(model.getRoot());
        assertEquals(53, model.getRoot().weight);
        assertEquals(7, model.getLeaves().size());
        assertEquals(13, model.getAllNodes().size());

        List<HuffmanTreeModel.CodeEntry> codes = model.getCodesList();
        for (int i = 0; i < codes.size(); i++) {
            for (int j = 0; j < codes.size(); j++) {
                if (i != j) {
                    assertFalse(codes.get(i).code.startsWith(codes.get(j).code));
                }
            }
        }
    }

    @Test
    public void testUniformDistributionTree() {
        int[] uniform = {10, 10, 10, 10, 10, 10, 10, 10};
        HuffmanTreeModel model = HuffmanTreeModel.build(uniform);

        assertNotNull(model.getRoot());
        assertEquals(80, model.getRoot().weight);
        assertEquals(8, model.getLeaves().size());
        assertEquals(15, model.getAllNodes().size());

        List<HuffmanTreeModel.CodeEntry> codes = model.getCodesList();
        for (HuffmanTreeModel.CodeEntry entry : codes) {
            assertEquals("Every codeword must have length 3 in a balanced 8-leaf tree", 3, entry.code.length());
        }
    }

    @Test
    public void testTreePlanarLayoutGeometry() {
        int[] clrs = {5, 9, 12, 13, 16, 45};
        HuffmanTreeModel model = HuffmanTreeModel.build(clrs);

        float left = 20f;
        float top = 100f;
        float width = 800f;
        float height = 400f;

        model.layout(left, top, width, height, false);

        // Verify parent is strictly between left and right children
        for (HuffmanTreeModel.Node node : model.getAllNodes()) {
            if (!node.isLeaf()) {
                assertNotNull(node.left);
                assertNotNull(node.right);
                assertTrue("Left child must be strictly left of parent", node.left.x < node.x);
                assertTrue("Right child must be strictly right of parent", node.right.x > node.x);
                assertTrue("Children must be lower than parent in Y", node.left.y > node.y);
                assertTrue("Children must be lower than parent in Y", node.right.y > node.y);
            }
        }
    }

    @Test
    public void testNodeStepCreationAndCodesOrdering() {
        int[] clrs = {5, 9, 12, 13, 16, 45};
        HuffmanTreeModel model = HuffmanTreeModel.build(clrs);

        // Verify leaves have stepCreated == 0
        for (HuffmanTreeModel.Node leaf : model.getLeaves()) {
            assertEquals("Leaf nodes must have stepCreated == 0", 0, leaf.stepCreated);
            assertNotNull("Leaf node character must not be null", leaf.character);
            assertFalse("Leaf node character must not be empty", leaf.character.isEmpty());
        }

        // Verify internal nodes have stepCreated in range 1..N-1
        int internalCount = model.getAllNodes().size() - model.getLeaves().size();
        assertEquals(5, internalCount);
        boolean[] seenSteps = new boolean[internalCount + 1];
        for (HuffmanTreeModel.Node node : model.getAllNodes()) {
            if (!node.isLeaf()) {
                assertTrue("stepCreated must be >= 1", node.stepCreated >= 1);
                assertTrue("stepCreated must be <= 5", node.stepCreated <= 5);
                seenSteps[node.stepCreated] = true;
            }
        }
        for (int s = 1; s <= internalCount; s++) {
            assertTrue("Every step 1..N-1 must correspond to a created internal node", seenSteps[s]);
        }
    }
}
