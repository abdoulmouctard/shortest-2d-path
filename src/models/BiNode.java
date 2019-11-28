package models;

public class BiNode<E> implements Comparable<BiNode<E>> {
    private E element;
    private BiNode<E> left;
    private BiNode<E> right;

    public BiNode(E element, BiNode<E> left, BiNode<E> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }

    public BiNode(E element) {
        this(element, null, null);
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public BiNode<E> getLeft() {
        return left;
    }

    public void setLeft(BiNode<E> left) {
        this.left = left;
    }

    public BiNode<E> getRight() {
        return right;
    }

    public void setRight(BiNode<E> right) {
        this.right = right;
    }

    public boolean hasLeftChild() {
        return this.left != null;
    }

    public boolean hasRightChild() {
        return this.right != null;
    }

    public int numberOfChild() {
        if (this.right == null && this.left == null) return 1;
        if (this.right == null) return 1 + this.left.numberOfChild();
        if (this.left == null) return 1 + this.right.numberOfChild();
        return 1 + this.left.numberOfChild() + this.right.numberOfChild();
    }

    @Override
    public int compareTo(BiNode<E> eBiNode) {
        return 0;
    }
}
