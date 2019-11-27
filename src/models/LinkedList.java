package models;

import java.util.function.BiPredicate;

public class LinkedList<E> {
    private Node<E> node;

    public LinkedList(Node<E> node) {
        this.node = node;
    }

    public Node<E> getNode() {
        return node;
    }

    public void setNode(Node<E> node) {
        this.node = node;
    }

    public boolean isEmpty() {
        return this.node == null;
    }


    public void insert(E element) {
        Node<E> node = lastNode();
        node.setNext(new Node<>(element, null));
    }

    public Node<E> lastNode() {
        return this.lastNodeOf(this.node);
    }

    public Node<E> lastNodeOf(Node<E> node) {
        if (node == null) return null; // maybe throw an exception here ?
        if (!node.hasNext()) return node;
        return lastNodeOf(node.getNext());
    }

    public int size() {
        Node node = this.node;
        int counter = 0;

        while (node.hasNext()) {
            counter++;
            node = node.getNext();
        }

        return counter;
    }

    public Node<E> getEvenNodes() {
        return getEvenNodes(this.node);
    }


    public Node<E> getOddNodes() {
        return getOddNodes(this.node);
    }


    public static <E> Node<E> getEvenNodes(Node<E> node) {
        if (node == null) return null;
        Node<E> next = node.hasNext() ? node.getNext().getNext() : null;
        return new Node<>(node.getElement(), getEvenNodes(next));
    }

    public static <E> Node<E> getOddNodes(Node<E> node) {
        if (node == null || !node.hasNext()) return null;
        return getEvenNodes(node.getNext());
    }

    public static <E> LinkedList<E> merge(LinkedList<E> first, LinkedList<E> second, BiPredicate<Node<E>, Node<E>> predicate) {
        return new LinkedList<E>(merge(first.node, second.node, predicate));
    }

    public static <E> LinkedList<E> merge(LinkedList<E> first, LinkedList<E> second) {
        return merge(first, second, (f, s) -> true);
    }

    public static <E> Node<E> merge(Node<E> first, Node<E> second) {
        return merge(first, second, (f, s) -> true);
    }

    public static <E> Node<E> merge(Node<E> first, Node<E> second, BiPredicate<Node<E>, Node<E>> predicate) {
        if (first == null) return second;
        if (second == null) return first;

        boolean test = predicate.test(first, second);

        E first_element = test ? first.getElement() : second.getElement();
        E second_element = test ? second.getElement() : first.getElement();

        return new Node<>(first_element, new Node<>(second_element, merge(first.getNext(), second.getNext())));
    }

}
