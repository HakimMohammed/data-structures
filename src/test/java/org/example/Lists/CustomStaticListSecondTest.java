package org.example.Lists;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class CustomStaticListSecondTest {

    @Test
    public void testAddAndSize() {
        CustomStaticList<Integer> list = new CustomStaticList<>(3);
        assertTrue(list.add(10));
        assertTrue(list.add(20));
        assertEquals(2, list.size());
    }

    @Test
    public void testAddThrowsWhenFull() {
        CustomStaticList<Integer> list = new CustomStaticList<>(2);
        list.add(1);
        list.add(2);
        assertThrows(RuntimeException.class, () -> list.add(3));
    }

    @Test
    public void testIsEmpty() {
        CustomStaticList<String> list = new CustomStaticList<>(5);
        assertTrue(list.isEmpty());
        list.add("A");
        assertFalse(list.isEmpty());
    }

    @Test
    public void testContains() {
        CustomStaticList<String> list = new CustomStaticList<>(5);
        list.add("A");
        list.add("B");

        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertFalse(list.contains("C"));
    }

    @Test
    public void testRemoveObject() {
        CustomStaticList<Integer> list = new CustomStaticList<>(5);
        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.remove((Integer) 20));
        assertEquals(2, list.size());
        assertFalse(list.contains(20));
    }

    @Test
    public void testRemoveIndex() {
        CustomStaticList<String> list = new CustomStaticList<>(5);
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals("B", list.remove(1));
        assertEquals(2, list.size());
        assertEquals(List.of("A", "C"), list.subList(0, 2));
    }

    @Test
    public void testGetSet() {
        CustomStaticList<String> list = new CustomStaticList<>(3);
        list.add("A");
        list.add("B");

        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));

        assertEquals("B", list.set(1, "C"));
        assertEquals("C", list.get(1));
    }

    @Test
    public void testGetThrowsInvalidIndex() {
        CustomStaticList<Integer> list = new CustomStaticList<>(2);
        list.add(1);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    public void testToArray() {
        CustomStaticList<Integer> list = new CustomStaticList<>(5);
        list.add(1);
        list.add(2);
        list.add(3);

        Object[] a = list.toArray();
        assertArrayEquals(new Object[]{1,2,3}, a);
    }

    @Test
    public void testIndexOfAndLastIndexOf() {
        CustomStaticList<String> list = new CustomStaticList<>(5);
        list.add("A");
        list.add("B");
        list.add("A");

        assertEquals(0, list.indexOf("A"));
        assertEquals(2, list.lastIndexOf("A"));
        assertEquals(-1, list.indexOf("Z"));
    }

    @Test
    public void testSubList() {
        CustomStaticList<String> list = new CustomStaticList<>(5);
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        List<String> sub = list.subList(1, 3);
        assertEquals(List.of("B", "C"), sub);
    }

    @Test
    public void testIterator() {
        CustomStaticList<Integer> list = new CustomStaticList<>(3);
        list.add(10);
        list.add(20);
        list.add(30);

        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals((int) 10, (int) it.next());
        assertEquals((int) 20, (int) it.next());
        assertEquals((int) 30, (int) it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorRemove() {
        CustomStaticList<Integer> list = new CustomStaticList<>(4);
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();
        it.next(); // 1
        it.remove(); // remove 1

        assertEquals(List.of(2, 3), list.subList(0, list.size()));
    }

    @Test
    public void testClear() {
        CustomStaticList<String> list = new CustomStaticList<>(4);
        list.add("A");
        list.add("B");

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddAll() {
        CustomStaticList<Integer> list = new CustomStaticList<>(5);
        list.add(1);
        list.add(2);

        assertTrue(list.addAll(List.of(3,4)));
        assertEquals(List.of(1,2,3,4), list.subList(0, list.size()));
    }

    @Test
    public void testAddAllFailsWhenNoSpace() {
        CustomStaticList<Integer> list = new CustomStaticList<>(3);
        list.add(1);
        list.add(2);

        assertFalse(list.addAll(List.of(3,4))); // too many elements
    }

}
