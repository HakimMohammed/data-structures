package org.example.Lists;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CustomStaticListTest {

    private CustomStaticList<String> list;
    private static final int INITIAL_CAPACITY = 5;

    /**
     * Set up a fresh instance of the list before each test. (JUnit 4: @Before)
     */
    @Before
    public void setUp() {
        // You might need to adjust the constructor call based on your CustomStaticList
        list = new CustomStaticList<>(INITIAL_CAPACITY);
    }

    // --- Basic Operations (size, isEmpty, add, get, clear) ---

    @Test
    public void testInitialState() {
        // A new list should be empty and have a size of 0.
        assertTrue("New list should be empty.", list.isEmpty());
        assertEquals("New list size should be 0.", 0, list.size());
    }

    @Test
    public void testAddAndGet() {
        list.add("Apple");
        list.add("Banana");

        assertEquals(2, list.size());
        assertFalse(list.isEmpty());
        assertEquals("Apple", list.get(0));
        assertEquals("Banana", list.get(1));
    }

    @Test
    public void testClear() {
        list.add("One");
        list.add("Two");
        list.clear();

        assertTrue("List should be empty after clear.", list.isEmpty());
        assertEquals(0, list.size());
    }

    // --- JUnit 4 style for expected exceptions ---
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetThrowsIndexOutOfBounds_EmptyList() {
        list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetThrowsIndexOutOfBounds_TooHigh() {
        list.add("Item");
        list.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetThrowsIndexOutOfBounds_Negative() {
        list.get(-1);
    }

    // --- Manipulation Operations (remove, set, add(index)) ---

    @Test
    public void testRemoveAtIndex() {
        list.add("A");
        list.add("B");
        list.add("C");

        String removed = list.remove(1); // Remove 'B'

        assertEquals("B", removed);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    public void testRemoveObject() {
        list.add("Red");
        list.add("Green");
        list.add("Blue");
        list.add("Red");

        assertTrue("First 'Red' should be successfully removed.", list.remove("Red"));
        assertEquals(3, list.size());
        assertEquals("Green", list.get(0));

        // Attempt to remove a non-existent element.
        assertFalse("Non-existent element should not be removed.", list.remove("Yellow"));
    }

    @Test
    public void testSet() {
        list.add("Old1");
        list.add("Old2");

        String oldElement = list.set(1, "New2");

        assertEquals("Old2", oldElement);
        assertEquals(2, list.size());
        assertEquals("New2", list.get(1));
    }

    @Test
    public void testAddAtIndex() {
        list.add("B");
        list.add("D");

        list.add(0, "A"); // Insert at the beginning
        list.add(2, "C"); // Insert in the middle
        list.add(4, "E"); // Insert at the end (index == size)

        assertEquals(5, list.size());
        assertEquals(Arrays.asList("A", "B", "C", "D", "E"), list);
    }

    // --- Query Operations (contains, indexOf, lastIndexOf) ---

    @Test
    public void testIndexOfAndLastIndexOf() {
        list.add("First");
        list.add("Second");
        list.add("First");

        assertEquals(0, list.indexOf("First"));
        assertEquals(2, list.lastIndexOf("First"));
        assertEquals(-1, list.indexOf("Missing"));
    }

    // --- Bulk Operations (addAll, removeAll, retainAll, containsAll) ---

    @Test
    public void testAddAll() {
        List<String> collectionToAdd = Arrays.asList("Three", "Four");
        list.add("One");

        assertTrue("addAll should return true if elements were added.", list.addAll(collectionToAdd));
        assertEquals(3, list.size());
        assertEquals("Four", list.get(2));

        assertFalse("addAll with empty collection should return false.", list.addAll(new ArrayList<>()));
    }

    // --- Iterator and ListIterator ---

    @Test
    public void testIteratorNextAndHasNext() {
        list.add("X");
        list.add("Y");
        Iterator<String> it = list.iterator();

        assertTrue(it.hasNext());
        assertEquals("X", it.next());
        assertTrue(it.hasNext());
        assertEquals("Y", it.next());
        assertFalse(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextThrowsException() {
        Iterator<String> it = list.iterator();
        it.next(); // Should throw if list is empty

        list.add("Item");
        it = list.iterator();
        it.next(); // Consume 'Item'
        it.next(); // Should throw at the end
    }

    @Test
    public void testIteratorRemove() {
        list.add("Keep");
        list.add("Remove");
        list.add("AlsoKeep");
        System.out.println(list);


        Iterator<String> it = list.iterator();

        it.next(); // "Keep"
        it.next(); // "Remove"
        it.remove(); // Remove "Remove"

        System.out.println(list);

        assertEquals(2, list.size());
        assertEquals("AlsoKeep", list.get(1)); // Check the shift
    }

    @Test(expected = IllegalStateException.class)
    public void testIteratorRemoveThrowsIllegalState() {
        list.add("A");
        Iterator<String> it = list.iterator();
        it.remove(); // Should fail because next() hasn't been called
    }

    @Test
    public void testListIteratorSetAndAdd() {
        list.add("One");
        list.add("Two");
        ListIterator<String> lit = list.listIterator();

        // Test previous/nextIndex
        assertEquals(0, lit.nextIndex());

        // Test set
        lit.next(); // "One"
        lit.set("Uno");
        assertEquals("Uno", list.get(0));

        // Test add
        lit.add("Dos");
        assertEquals(3, list.size());
        assertEquals("Dos", list.get(1));
    }
}