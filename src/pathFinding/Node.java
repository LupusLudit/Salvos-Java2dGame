package pathFinding;

/**
 * The type Node.
 */
public class Node {

    private Node parent;
    private int col;
    private int row;
    private int f;
    private int g;
    private int h;
    private boolean solid = false;
    private boolean open = false;
    private boolean closed = false;

    /**
     * Instantiates a new Node.
     *
     * @param col the col
     * @param row the row
     */
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }


    /**
     * Gets parent.
     *
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets parent.
     *
     * @param parent the parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Gets col.
     *
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets f.
     *
     * @return the f
     */
    public int getF() {
        return f;
    }

    /**
     * Sets f.
     *
     * @param f the f
     */
    public void setF(int f) {
        this.f = f;
    }

    /**
     * Gets g.
     *
     * @return the g
     */
    public int getG() {
        return g;
    }

    /**
     * Sets g.
     *
     * @param g the g
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * Gets h.
     *
     * @return the h
     */
    public int getH() {
        return h;
    }

    /**
     * Sets h.
     *
     * @param h the h
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Is solid boolean.
     *
     * @return the boolean
     */
    public boolean isSolid() {
        return solid;
    }

    /**
     * Sets solid.
     *
     * @param solid the solid
     */
    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    /**
     * Is open boolean.
     *
     * @return the boolean
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Sets open.
     *
     * @param open the open
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Is closed boolean.
     *
     * @return the boolean
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Sets closed.
     *
     * @param closed the closed
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
