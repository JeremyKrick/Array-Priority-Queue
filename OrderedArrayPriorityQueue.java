/*
Program #1
Jeremy Krick
cssc0915
*/

package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private int maxSize, currentSize;
    private E[] storage;

    public OrderedArrayPriorityQueue() {
        this(DEFAULT_MAX_CAPACITY);
    }

    public OrderedArrayPriorityQueue(int size) {
        currentSize = 0;
        maxSize = size;
        storage = (E[]) new Comparable[maxSize];
    }

    public boolean insert(E object) {
        if (isFull()) return false;
        int where = findInsertionPoint(object, 0, currentSize-1);
        for (int i = currentSize-1; i >= where; i--)
            storage[i+1] = storage[i];
        storage[where] = object;
        currentSize++;
        return true;
    }

    public E remove() {
        if (isEmpty()) return null;
        return storage[--currentSize];
    }

    public boolean delete(E obj) {
        int where = find(obj, 0, currentSize-1);
        if (where != -1) {
            for (int i = where; i < currentSize; i++)
                storage[i] = storage[i+1];
            currentSize--;
        }
        else
            return false;
        return delete(obj);
    }

    public E peek() {
        if (isEmpty() ) return null;
        return storage[currentSize-1];
    }

    public boolean contains(E obj) {
        return find(obj, 0, currentSize-1) != -1;
    }

    private int find(E obj, int lo, int hi) {
        if (hi < lo) return -1;
        int mid = (lo+hi) >> 1;
        if (obj.compareTo(storage[mid]) == 0)
            return mid;
        if (obj.compareTo(storage[mid]) < 0)
            return find(obj, mid+1, hi);
        return find(obj, lo, mid-1);
    }

    private int findInsertionPoint(E obj, int lo, int hi) {
        if (hi < lo) return lo;
        int mid = (lo+hi) >> 1;
        if (obj.compareTo(storage[mid]) >= 0)
            return findInsertionPoint(obj, lo, mid-1);
        return findInsertionPoint(obj, mid+1, hi);
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