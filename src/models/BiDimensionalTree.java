package models;

public class BiDimensionalTree<E> {
    private BiNode<E> root;

    public BiDimensionalTree(BiNode<E> root) {
        this.root = root;
    }

    public int numberOfChild() {
        assert this.root != null;
        return this.root.numberOfChild();
    }
}
