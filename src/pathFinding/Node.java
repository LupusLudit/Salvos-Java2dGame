package pathFinding;

/**
 * Node for A* search
 */
public class Node {

    private Node parent;
    private int col;
    private int row;
    private int f; // combined value of distances g and h
    private int g; // distance from start Node
    private int h; // distance from end Node
    private boolean solid = false;
    private boolean open = false;
    private boolean closed = false;
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }
    public int getF() {
        return f;
    }
    public void setF(int f) {
        this.f = f;
    }
    public int getG() {
        return g;
    }
    public void setG(int g) {
        this.g = g;
    }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public boolean isSolid() {
        return solid;
    }
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public boolean isClosed() {
        return closed;
    }
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
