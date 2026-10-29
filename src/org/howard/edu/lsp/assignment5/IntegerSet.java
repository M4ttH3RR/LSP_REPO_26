package org.howard.edu.lsp.assignment5;

import java.util.Objects;
import java.util.TreeSet;

/**
 * Represents a mathematical set of integers. A set never contains duplicate
 * values. Elements are stored internally in a sorted {@link TreeSet}, which
 * keeps the representation encapsulated and makes ordered output and
 * smallest/largest lookups straightforward.
 *
 * <p>All operations that return an {@code IntegerSet} return a new,
 * independent object and never modify either input set.</p>
 */
public class IntegerSet {

    /** Internal storage; never exposed outside this class. */
    private final TreeSet<Integer> elements = new TreeSet<>();

    /**
     * Constructs an empty set.
     */
    public IntegerSet() {
    }

    /**
     * Removes all elements from this set.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Returns the number of elements in this set.
     *
     * @return the count of distinct elements
     */
    public int length() {
        return elements.size();
    }

    /**
     * Compares this set with another for equality. Two sets are equal when they
     * contain exactly the same elements, regardless of insertion order.
     *
     * @param b the set to compare with
     * @return {@code true} if both sets contain the same elements
     */
    public boolean equals(IntegerSet b) {
        if (this == b) {
            return true;
        }
        if (b == null) {
            return false;
        }
        return elements.equals(b.elements);
    }

    /**
     * Compares this set with an arbitrary object. Returns {@code true} only if
     * the object is an {@code IntegerSet} with the same elements.
     *
     * @param obj the object to compare with
     * @return {@code true} if {@code obj} is an equal {@code IntegerSet}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IntegerSet) && equals((IntegerSet) obj);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code of this set
     */
    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    /**
     * Determines whether this set contains the given value.
     *
     * @param value the value to look for
     * @return {@code true} if the value is present
     */
    public boolean contains(int value) {
        return elements.contains(value);
    }

    /**
     * Returns the largest element in this set.
     *
     * @return the largest integer in the set
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return elements.last();
    }

    /**
     * Returns the smallest element in this set.
     *
     * @return the smallest integer in the set
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return elements.first();
    }

    /**
     * Adds an item to this set. Duplicate values are ignored.
     *
     * @param item the value to add
     */
    public void add(int item) {
        elements.add(item);
    }

    /**
     * Removes an item from this set. Has no effect if the item is absent.
     *
     * @param item the value to remove
     */
    public void remove(int item) {
        elements.remove(item);
    }

    /**
     * Returns the union of this set and {@code b}: elements in either set.
     *
     * @param b the other set
     * @return a new set containing all elements of both sets
     */
    public IntegerSet union(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = copy();
        result.elements.addAll(b.elements);
        return result;
    }

    /**
     * Returns the intersection of this set and {@code b}: elements in both sets.
     *
     * @param b the other set
     * @return a new set containing the shared elements
     */
    public IntegerSet intersect(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = copy();
        result.elements.retainAll(b.elements);
        return result;
    }

    /**
     * Returns the difference of this set and {@code b}: elements in this set
     * that are not in {@code b}.
     *
     * @param b the set whose elements are excluded
     * @return a new set containing this set's elements not found in {@code b}
     */
    public IntegerSet diff(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = copy();
        result.elements.removeAll(b.elements);
        return result;
    }

    /**
     * Returns the assignment-specific complement: elements in {@code b} that
     * are not in this set.
     *
     * @param b the set from which elements are taken
     * @return a new set containing elements of {@code b} not in this set
     */
    public IntegerSet complement(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        return b.diff(this);
    }

    /**
     * Determines whether every element of this set is also in {@code b}.
     * The empty set is a subset of every set.
     *
     * @param b the candidate superset
     * @return {@code true} if this set is a subset of {@code b}
     */
    public boolean isSubsetOf(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        return b.elements.containsAll(elements);
    }

    /**
     * Determines whether this set and {@code b} share no elements.
     * Empty sets are disjoint from all sets.
     *
     * @param b the other set
     * @return {@code true} if the sets have no elements in common
     */
    public boolean isDisjoint(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        for (Integer value : elements) {
            if (b.elements.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the symmetric difference: elements that occur in exactly one of
     * this set and {@code b}.
     *
     * @param b the other set
     * @return a new set containing elements found in exactly one of the sets
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        Objects.requireNonNull(b, "b must not be null");
        IntegerSet result = union(b);
        result.elements.removeAll(intersect(b).elements);
        return result;
    }

    /**
     * Determines whether this set has no elements.
     *
     * @return {@code true} if the set is empty
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the elements in ascending order, separated by a comma and a
     * space, inside square brackets, e.g. {@code [-3, 0, 8, 15]}. An empty set
     * yields {@code []}.
     *
     * @return the formatted string representation of this set
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Integer value : elements) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(value);
            first = false;
        }
        return sb.append(']').toString();
    }

    /**
     * Creates an independent copy of this set.
     *
     * @return a new set with the same elements
     */
    private IntegerSet copy() {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(elements);
        return result;
    }
}