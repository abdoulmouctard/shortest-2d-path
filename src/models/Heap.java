package models;

public class Heap<E> {

    private E[] heap;

    public Heap(int capacity) {
        // heap = new E[capacity];

    }

    public boolean insert(E parent, E element, int index) {

        assert index >= 0 && index < heap.length;

        int parent_index = this.indexOf(parent);

        if (parent_index == -1) return false;

        this.heap[index] = element;

        return true;
    }

    public boolean update(E element_old, E element_new) {
        int index = this.indexOf(element_old);

        if (index == -1) return false;

        heap[index] = element_new;

        return true;
    }

    public boolean delete(E element) {
        return this.delete(this.indexOf(element));
    }

    public boolean delete(int index) {
        if (index == -1) return false;
        heap[index] = null;
        return true;
    }

    public int indexOf(E element) {
        for (int i = 0; i < heap.length; i++) {
            if (element.equals(heap[i])) return i;
        }
        return -1;
    }
}
