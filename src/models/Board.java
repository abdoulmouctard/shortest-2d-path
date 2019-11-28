package models;

public class Board {
    private static final int DEFAULT_WIDTH = 489;
    private static final int DEFAULT_HEIGHT = 489;

    private int width;
    private int height;

    private LinkedList<Point> nodeLinkedList;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        Node<Point> firstNode = new Node<>(new Point(0, 0));
        this.nodeLinkedList = new LinkedList<Point>(firstNode);
    }

    public Board() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LinkedList<Point> getNodeLinkedList() {
        return nodeLinkedList;
    }
}
