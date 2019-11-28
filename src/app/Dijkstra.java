package app;

import models.Arc;
import models.Heap;
import models.LinkedList;
import models.Point;

class Dijkstra extends Algorithm<Heap<Point>> {
    private Heap heap;
    private LinkedList<Arc> arcLinkedList;


    public Dijkstra() {
        this.heap = new Heap(100);
        this.arcLinkedList = new LinkedList<>(null);
    }

    @Override
    void execute(Heap<Point> dataStructure, Point departure, Point arrival) {
        
    }
}
