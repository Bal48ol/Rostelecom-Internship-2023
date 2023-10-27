package org.fubar;

import java.util.Iterator;
import java.util.NoSuchElementException;

class MyList<E> implements Iterable<E> {
    private Object[] elements;
    private int size;

    public MyList() {
        elements = new Object[10];
        size = 0;
    }

    public void add(E element) {
        if (size == elements.length) {
            Object[] newElements = new Object[size * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
        elements[size++] = element;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements Iterator<E> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (E) elements[currentIndex++];
        }
    }
}
