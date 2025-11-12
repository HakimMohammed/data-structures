package org.example.Lists;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("unchecked")
public class CustomStaticList<T> implements List<T>, Serializable {

    private final int size;
    private int capacity = 0;
    private T[] data;

    public CustomStaticList(int size) {
        this.size = size;
        this.data = (T[]) new Object[size];
    }

    public CustomStaticList(int size, T[] data) {
        this.size = size;
        this.data = data;
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return capacity == 0;
    }

    @Override
    public boolean contains(Object o) {
        T item = (T) o;
        for (int i = 0; i < capacity; i++) {
            if (data[i].equals(item)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() throws NoSuchElementException {
        return new Iterator<T>() {
            int index = 0;
            boolean nextCalled = false;

            @Override
            public boolean hasNext() {
                return index < CustomStaticList.this.capacity;
            }

            @Override
            public T next() {
                nextCalled = true;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[index++];
            }

            @Override
            public void remove() throws IllegalStateException {
                if (!nextCalled) {
                    throw new IllegalStateException("next() has not been called");
                }
                CustomStaticList.this.remove(index - 1);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[capacity];
        System.arraycopy(data, 0, newArray, 0, capacity);
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        for (int i = 0; i < capacity; i++) {
            a[i] = (T1) data[i];
        }
        return a;
    }

    @Override
    public boolean add(T t) throws RuntimeException {
        if (capacity < size) {
            data[capacity] = t;
            capacity++;
            return true;
        }
        throw new RuntimeException("Full List: Cannot add more items");
    }

    @Override
    public boolean remove(Object o) {
        T item = (T) o;
        for (int i = 0; i < capacity; i++) {
            if (data[i].equals(item)) {
                for (int j = i; j < capacity - 1; j++) {
                    data[j] = data[j + 1];
                }
                data[capacity] = null;
                capacity--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!contains(object)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.size() > size - capacity || c.isEmpty()) {
            return false;
        }
        try {
            for (T element : c) {
                CustomStaticList.this.add(element);
            }
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int cSize = c.size();
        if (cSize > size - capacity) {
            for (int i = index; i < cSize; i++) {
                data[i + cSize] = data[i];
            }
            T[] newArray = (T[]) c.toArray();
            System.arraycopy(newArray, 0, data, index, c.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        T[] newArray = (T[]) c.toArray();
        for (T item : newArray) {
            if (!remove(item)) return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        T[] newArray = (T[]) c.toArray();
        for (T item : newArray) {
            if (!contains(item)) {
                if (!remove(item)) return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        capacity = 0;
        data = (T[]) new Object[size];
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= capacity) {
            throw new IndexOutOfBoundsException("Item with that index does not exist");
        }
        return data[index];
    }

    @Override
    public T set(int index, T element) throws IndexOutOfBoundsException {
        if (index >= capacity) {
            throw new IndexOutOfBoundsException("Item with that index does not exist");
        }
        T item = data[index];
        data[index] = element;
        return item;
    }

    @Override
    public void add(int index, T element) throws RuntimeException {
        if (index > capacity) {
            throw new IndexOutOfBoundsException("Index is out of bands");
        }
        if (size - capacity < 1) {
            throw new RuntimeException("List if already full");
        }
        if (data[index] != null) {
            for (int i = capacity + 1; i > index; i--) {
                data[i] = data[i - 1];
            }
        }
        data[index] = element;
        capacity++;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index >= capacity) {
            throw new IndexOutOfBoundsException("Index is out of bands");
        }
        T temp = data[index];
        for (int j = index; j < capacity - 1; j++) {
            data[j] = data[j + 1];
        }
        data[capacity - 1] = null;
        capacity--;
        return temp;
    }

    @Override
    public int indexOf(Object o) {
        T item = (T) o;
        for (int i = 0; i < capacity; i++) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        T item = (T) o;
        int lastIndex = -1;
        for (int i = 0; i < capacity; i++) {
            if (data[i].equals(item)) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {

            int index = 0;
            int lastCalledIndex = 0;

            @Override
            public boolean hasNext() {
                return index < CustomStaticList.this.capacity;
            }

            @Override
            public T next() {
                lastCalledIndex = index;
                return data[index++];
            }

            @Override
            public boolean hasPrevious() {
                return index > 0;
            }

            @Override
            public T previous() {
                lastCalledIndex = index - 1;
                return data[--index];
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                CustomStaticList.this.remove(data[lastCalledIndex]);
            }

            @Override
            public void set(T t) {
                CustomStaticList.this.set(lastCalledIndex, t);
            }

            @Override
            public void add(T t) {
                CustomStaticList.this.add(lastCalledIndex + 1, t);
            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>() {

            @Override
            public boolean hasNext() {
                return index < capacity;
            }

            @Override
            public T next() {
                return data[index];
            }

            @Override
            public boolean hasPrevious() {
                return index > 0;
            }

            @Override
            public T previous() {
                return data[index - 1];
            }

            @Override
            public int nextIndex() {
                return index + 1;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                CustomStaticList.this.remove(data[CustomStaticList.this.capacity]);
            }

            @Override
            public void set(T t) {
                CustomStaticList.this.set(CustomStaticList.this.indexOf(t), t);
            }

            @Override
            public void add(T t) {
                CustomStaticList.this.add(t);
            }
        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) throws RuntimeException {
        if (fromIndex < 0 || fromIndex > toIndex || toIndex > capacity) {
            throw new IndexOutOfBoundsException("Not a valid index while sub listing");
        }
        int newSize = toIndex - fromIndex + 1;
        CustomStaticList<T> newList = new CustomStaticList<T>(newSize);

        for (int i = fromIndex; i <= toIndex; i++) {
            newList.add(data[i]);
        }

        return newList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (T item : data) {
            stringBuilder.append(item);
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    ;
}
