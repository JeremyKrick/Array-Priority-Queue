/*
Program #1
Jeremy Krick
cssc0915
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private int maxSize, currentSize;
    private E[] storage;

    public UnorderedArrayPriorityQueue() {
        this(DEFAULT_MAX_CAPACITY);
    }

    public UnorderedArrayPriorityQueue(int size) {
        currentSize = 0;
        maxSize = size;
        storage = (E[]) new Comparable[maxSize];
    }

    public boolean insert(E object) {
        if (isFull()) return false;
        storage[currentSize++] = object;
        return true;
    }

    public E remove() {
        if (isEmpty()) return null;
        E tmp = storage[findBest()];
        for (int i = findBest(); i < currentSize-1; i++)
            storage[i] = storage[i+1];
        currentSize--;
        return tmp;
    }

    public boolean delete(E obj) {
        boolean didDelete = false;
        for (int i = 0; i < currentSize; i++)
            if (storage[i].compareTo(obj) == 0) {
                didDelete = true;
                for (int j = i--; j < currentSize-1; j++)
                    storage[j] = storage[j+1];
                currentSize--;
            }
        return didDelete;
    }

    public E peek() {
        if (isEmpty()) return null;
        return storage[findBest()];
    }

    public boolean contains(E obj) {
        return find(obj) != null;
    }

    private E find (E obj) {
        for (int i = 0; i < currentSize; i++)
            if (storage[i].compareTo(obj) == 0)
                return storage[i];
        return null;
    }

    private int findBest() {
        int best = 0;
        for (int i = 0; i < currentSize; i++)
            if (storage[i].compareTo(storage[best]) < 0)
                best = i;
        return best;
    }

    public int size() {return currentSize;}

    public void clear() {currentSize = 0;}

    public boolean isEmpty() {return (currentSize == 0);}

    public boolean isFull() {return (currentSize == maxSize);}

    public Iterator<E> iterator() {return new IteratorHelper();}

    class IteratorHelper implements Iterator<E> {
        int iterIndex;

        public IteratorHelper() {iterIndex = 0;}

        public boolean hasNext() {return iterIndex != currentSize;}

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return storage[iterIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}