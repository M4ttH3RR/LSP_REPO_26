
package org.howard.edu.lsp.assignment6;
 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 
/**
 * Assignment 6: JUnit 5 tests for IntegerSet.
 *
 * Every test builds its own sets (Arrange), invokes the operation (Act),
 * and verifies results with assertions (Assert). No shared mutable state.
 */
public class IntegerSetTest {
 
    /** Helper: builds a new set containing the given values. */
    private static IntegerSet setOf(int... values) {
        IntegerSet s = new IntegerSet();
        for (int v : values) {
            s.add(v);
        }
        return s;
    }
 
    // ===== clear() =====
 
    @Test
    public void testClearClearsPopulatedSet() {
        IntegerSet s = setOf(1, 2, 3);
        s.clear();
        assertTrue(s.isEmpty());
        assertEquals(0, s.length());
        assertEquals("[]", s.toString());
    }
 
    @Test
    public void testClearClearsEmptySet() {
        IntegerSet s = new IntegerSet();
        s.clear();
        assertTrue(s.isEmpty());
        assertEquals(0, s.length());
    }
 
    // ===== length() =====
 
    @Test
    public void testLengthCountsUniqueElements() {
        IntegerSet s = new IntegerSet();
        s.add(1);
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(3);
        assertEquals(3, s.length());
    }
 
    @Test
    public void testLengthEmptySetLength() {
        assertEquals(0, new IntegerSet().length());
    }
 
    // ===== equals() =====
 
    @Test
    public void testEqualsIgnoresInsertionOrder() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 1, 2);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
 
    @Test
    public void testEqualsDetectsDifferentContents() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(1, 2, 4);
        assertEquals(a.length(), b.length());
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertNotEquals(a, b);
    }
 
    @Test
    public void testEqualsEmptySetsEqual() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();
        assertTrue(a.equals(b));
        assertEquals(a, b);
    }
 
    @Test
    public void testEqualsSameObjectAndDifferentSizes() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(1, 2);
        assertTrue(a.equals(a));
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertFalse(a.equals((IntegerSet) null));
        assertFalse(a.equals((Object) "not a set"));
    }
 
    // ===== contains() =====
 
    @Test
    public void testContainsPresentAndAbsent() {
        IntegerSet s = setOf(5, 10, 15);
        assertTrue(s.contains(10));
        assertFalse(s.contains(11));
    }
 
    @Test
    public void testContainsEmptySetContainsNothing() {
        IntegerSet s = new IntegerSet();
        assertFalse(s.contains(0));
        assertFalse(s.contains(42));
    }
 
    // ===== largest() =====
 
    @Test
    public void testLargestNegativeAndPositiveValues() {
        assertEquals(9, setOf(-5, 0, 3, 9, -20).largest());
        assertEquals(-2, setOf(-5, -2, -9).largest());
    }
 
    @Test
    public void testLargestEmptySetThrowsException() {
        IntegerSet s = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> s.largest());
    }
 
    // ===== smallest() =====
 
    @Test
    public void testSmallestNegativeAndPositiveValues() {
        assertEquals(-20, setOf(-5, 0, 3, 9, -20).smallest());
        assertEquals(4, setOf(4, 7, 9).smallest());
    }
 
    @Test
    public void testSmallestEmptySetThrowsException() {
        IntegerSet s = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> s.smallest());
    }
 
    // ===== add() =====
 
    @Test
    public void testAddIgnoresDuplicates() {
        IntegerSet s = new IntegerSet();
        s.add(7);
        s.add(7);
        s.add(7);
        assertEquals(1, s.length());
        assertTrue(s.contains(7));
        assertEquals("[7]", s.toString());
    }
 
    @Test
    public void testAddHandlesIntegerBoundaries() {
        IntegerSet s = new IntegerSet();
        s.add(Integer.MIN_VALUE);
        s.add(Integer.MAX_VALUE);
        s.add(0);
        assertEquals(3, s.length());
        assertTrue(s.contains(Integer.MIN_VALUE));
        assertTrue(s.contains(Integer.MAX_VALUE));
        assertTrue(s.contains(0));
        assertEquals(Integer.MIN_VALUE, s.smallest());
        assertEquals(Integer.MAX_VALUE, s.largest());
    }
 
    // ===== remove() =====
 
    @Test
    public void testRemoveRemovesExistingElement() {
        IntegerSet s = setOf(1, 2, 3);
        s.remove(2);
        assertFalse(s.contains(2));
        assertTrue(s.contains(1));
        assertTrue(s.contains(3));
        assertEquals(2, s.length());
    }
 
    @Test
    public void testRemoveIgnoresAbsentElement() {
        IntegerSet s = setOf(1, 2, 3);
        s.remove(99);
        assertEquals(3, s.length());
        assertEquals(setOf(1, 2, 3), s);
    }
 
    // ===== union() =====
 
    @Test
    public void testUnionOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);
        IntegerSet u = a.union(b);
        assertEquals(setOf(1, 2, 3, 4, 5), u);
        assertEquals(5, u.length());
    }
 
    @Test
    public void testUnionEmptyAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        assertEquals(setOf(1, 2, 3), a.union(empty));
        assertEquals(setOf(1, 2, 3), empty.union(a));
        assertEquals(setOf(1, 2, 3), a.union(a));
        assertTrue(empty.union(new IntegerSet()).isEmpty());
    }
 
    @Test
    public void testUnionDoesNotMutateInputs() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);
        IntegerSet u = a.union(b);
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(3, 4, 5), b);
        assertNotSame(a, u);
        assertNotSame(b, u);
        u.add(100);
        u.remove(1);
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(3, 4, 5), b);
    }
 
    // ===== intersect() =====
 
    @Test
    public void testIntersectOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);
        assertEquals(setOf(1, 2, 3, 4), a);
        assertEquals(setOf(3, 4, 5, 6), b);
        assertEquals(setOf(3, 4), a.intersect(b));
        assertEquals(setOf(3, 4), b.intersect(a));
    }
 
    @Test
    public void testIntersectDisjointAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(4, 5, 6);
        assertTrue(a.intersect(b).isEmpty());
        assertEquals(setOf(1, 2, 3), a.intersect(a));
        assertEquals(setOf(1, 2, 3), a);
    }
 
    @Test
    public void testIntersectIndependentResult() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(2, 3, 4);
        IntegerSet r = a.intersect(b);
        assertNotSame(a, r);
        assertNotSame(b, r);
        assertEquals(setOf(2, 3), r);
        r.add(99);
        r.remove(2);
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(2, 3, 4), b);
    }
 
    // ===== diff() =====
 
    @Test
    public void testDiffDirectionMatters() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);
        assertEquals(setOf(1, 2), a.diff(b));
        assertEquals(setOf(5, 6), b.diff(a));
        assertNotEquals(a.diff(b), b.diff(a));
    }
 
    @Test
    public void testDiffEmptyAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        assertEquals(setOf(1, 2, 3), a.diff(empty));
        assertTrue(empty.diff(a).isEmpty());
        assertTrue(a.diff(a).isEmpty());
        assertEquals(setOf(1, 2, 3), a);
    }
 
    @Test
    public void testDiffIndependentResult() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4);
        IntegerSet r = a.diff(b);
        assertEquals(setOf(1, 2), r);
        assertNotSame(a, r);
        assertNotSame(b, r);
        r.add(50);
        r.remove(1);
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(3, 4), b);
    }
 
    // ===== complement() =====
 
    @Test
    public void testComplementReverseDifference() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);
        assertEquals(setOf(5, 6), a.complement(b));
        assertEquals(setOf(1, 2), b.complement(a));
        assertEquals(b.diff(a), a.complement(b));
    }
 
    @Test
    public void testComplementEmptyOperands() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        assertEquals(setOf(1, 2, 3), empty.complement(a));
        assertTrue(a.complement(empty).isEmpty());
        assertTrue(empty.complement(new IntegerSet()).isEmpty());
    }
 
    @Test
    public void testComplementIndependentResult() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3, 4);
        IntegerSet r = a.complement(b);
        assertEquals(setOf(3, 4), r);
        assertNotSame(a, r);
        assertNotSame(b, r);
        r.add(77);
        r.remove(3);
        assertEquals(setOf(1, 2), a);
        assertEquals(setOf(2, 3, 4), b);
    }
 
    // ===== isSubsetOf() =====
 
    @Test
    public void testIsSubsetOfProperSubset() {
        IntegerSet small = setOf(1, 2);
        IntegerSet big = setOf(1, 2, 3, 4);
        assertTrue(small.isSubsetOf(big));
        assertFalse(big.isSubsetOf(small));
    }
 
    @Test
    public void testIsSubsetOfNotSubset() {
        IntegerSet a = setOf(1, 2, 9);
        IntegerSet b = setOf(1, 2, 3, 4);
        assertFalse(a.isSubsetOf(b));
    }
 
    @Test
    public void testIsSubsetOfEqualSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 2, 1);
        assertTrue(a.isSubsetOf(b));
        assertTrue(b.isSubsetOf(a));
    }
 
    @Test
    public void testIsSubsetOfEmptyCases() {
        IntegerSet empty = new IntegerSet();
        IntegerSet a = setOf(1, 2);
        assertTrue(empty.isSubsetOf(a));
        assertFalse(a.isSubsetOf(empty));
        assertTrue(empty.isSubsetOf(new IntegerSet()));
    }
 
    // ===== isDisjoint() =====
 
    @Test
    public void testIsDisjointNoOverlap() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(4, 5, 6);
        assertTrue(a.isDisjoint(b));
        assertTrue(b.isDisjoint(a));
    }
 
    @Test
    public void testIsDisjointOverlap() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);
        assertFalse(a.isDisjoint(b));
        assertFalse(b.isDisjoint(a));
    }
 
    @Test
    public void testIsDisjointEmptyAndSameOperand() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        assertTrue(a.isDisjoint(empty));
        assertTrue(empty.isDisjoint(a));
        assertFalse(a.isDisjoint(a));
    }
 
    // ===== symmetricDiff() =====
 
    @Test
    public void testSymmetricDiffPartiallyOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);
        assertEquals(setOf(1, 2, 5, 6), a.symmetricDiff(b));
        assertEquals(setOf(1, 2, 5, 6), b.symmetricDiff(a));
    }
 
    @Test
    public void testSymmetricDiffIdenticalSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(1, 2, 3);
        assertTrue(a.symmetricDiff(b).isEmpty());
        assertTrue(a.symmetricDiff(a).isEmpty());
    }
 
    @Test
    public void testSymmetricDiffEmptyAndDisjointSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();
        IntegerSet c = setOf(7, 8);
        assertEquals(setOf(1, 2, 3), a.symmetricDiff(empty));
        assertEquals(setOf(1, 2, 3), empty.symmetricDiff(a));
        assertEquals(setOf(1, 2, 3, 7, 8), a.symmetricDiff(c));
    }
 
    @Test
    public void testSymmetricDiffIndependentResult() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4);
        IntegerSet r = a.symmetricDiff(b);
        assertEquals(setOf(1, 2, 4), r);
        assertNotSame(a, r);
        assertNotSame(b, r);
        r.add(99);
        r.remove(1);
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(3, 4), b);
    }
 
    // ===== isEmpty() =====
 
    @Test
    public void testIsEmptyNewSet() {
        assertTrue(new IntegerSet().isEmpty());
    }
 
    @Test
    public void testIsEmptyPopulatedSet() {
        IntegerSet s = new IntegerSet();
        s.add(1);
        assertFalse(s.isEmpty());
    }
 
    @Test
    public void testIsEmptyAfterClear() {
        IntegerSet s = setOf(1, 2, 3);
        assertFalse(s.isEmpty());
        s.clear();
        assertTrue(s.isEmpty());
    }
 
    // ===== toString() =====
 
    @Test
    public void testToStringAscendingOrder() {
        IntegerSet s = setOf(15, -3, 8, 0);
        assertEquals("[-3, 0, 8, 15]", s.toString());
    }
 
    @Test
    public void testToStringEmptyFormatting() {
        assertEquals("[]", new IntegerSet().toString());
    }
 
    @Test
    public void testToStringExactSpacingAndDuplicates() {
        IntegerSet s = new IntegerSet();
        s.add(2);
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(1);
        assertEquals("[1, 2, 3]", s.toString());
        assertEquals("[5]", setOf(5, 5).toString());
    }
}
 