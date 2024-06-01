package pathFinding;

import logic.ApplicationPanel;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Search class representing the A* search algorithm for pathfinding.
 * This search algorithm was not created by me, but by Peter Hart, Nils Nilsson and Bertram Raphael in 1968
 * For more information about the search itself visit:
 * https://en.wikipedia.org/wiki/A*_search_algorithm
 * https://www.geeksforgeeks.org/a-search-algorithm/
 *
 * Source of inspiration for this code:
 * https://www.youtube.com/watch?v=Hd0D68guFKg&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=44
 * https://www.youtube.com/watch?v=2JNEme00ZFA
 */

public class Search {
    ApplicationPanel panel;
    private Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    Stack<Node> path = new Stack<>();
    private Node current, start, goal;
    private boolean finished;
    private int numOfAttempts = 750;
    private boolean pathPossible = false;
    public Search(ApplicationPanel panel) {
        this.panel = panel;
    }

    /**
     * Initializes nodes 2D Array.
     * Clears openList ArrayList and path Stack.
     */
    private void initialize(){
        nodes = new Node[panel.getTilePainter().getMapWidth()][panel.getTilePainter().getMapHeight()];
        for (int i = 0; i < panel.getTilePainter().getMapHeight(); i++){
            for (int j = 0; j < panel.getTilePainter().getMapWidth(); j++){
                nodes[j][i] = new Node(j,i);
            }
        }
        openList.clear();
        path.clear();
        finished = false;
    }

    /**
     * (Re)sets the start and goal nodes for pathfinding.
     * Also sets solid and non-solid nodes.
     *
     * @param startCol the column of the starting node
     * @param startRow the row of the starting node
     * @param goalCol the column of the goal node
     * @param goalRow the row of the goal node
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        initialize();
        start = nodes[startCol][startRow];
        current = start;
        goal = nodes[goalCol][goalRow];
        openList.add(start);

        for (int i = 0; i < panel.getTilePainter().getMapHeight(); i++) {
            for (int j = 0; j < panel.getTilePainter().getMapWidth(); j++) {
                if (panel.getTilePainter().getTile(j,i).isSolid()){
                    nodes[j][i].setSolid(true);
                }
                setDistanceValues(nodes[j][i]);
            }
        }
    }
    /**
     * Sets distance values (G, H, F) for a given node.
     *
     * @param node the node for which to set distance values.
     */
    public void setDistanceValues(Node node){
        node.setG(Math.abs(node.getCol() - start.getCol()) + Math.abs(node.getRow() - start.getRow()));
        node.setH(Math.abs(node.getCol() - goal.getCol()) + Math.abs(node.getRow() - goal.getRow()));

        node.setF(node.getG() + node.getH());
    }

    /**
     * Performs the A* search algorithm to find a path from the start node to the goal node.
     * This method iteratively explores nodes around the entity.
     * It updates states of nodes around this entity, and founds the most optimal path.
     * This path is then stored in Stack ( public void findPath()) where can later be accessed to tell the entity which way to go.
     */
    public void checkNodes(){
        int col;
        int row;
        int bestIndex;
        int minFValue;
        pathPossible = false;
        finished = false;
        numOfAttempts = 750;
        while (!finished && numOfAttempts > 0){
            col = current.getCol();
            row = current.getRow();
            current.setClosed(true);
            openList.remove(current);
            checkAdjacentNodes(col, row);

            bestIndex = 0;
            minFValue = Integer.MAX_VALUE;
            for (int i = 0; i < openList.size(); i++){
                if (openList.get(i).getF() < minFValue){
                    bestIndex = i;
                    minFValue = openList.get(i).getF();
                }else if (openList.get(i).getF() == minFValue){
                    if(openList.get(i).getG() < openList.get(bestIndex).getG()){
                        bestIndex = i;
                    }
                }
            }
            if (openList.isEmpty()){
                finished = true;
                numOfAttempts = 0;
                break;
            }
            current = openList.get(bestIndex);

            if (current == goal){
                finished = true;
                pathPossible = true;
                numOfAttempts = 0;
                findPath();
            }
            numOfAttempts--;
        }
    }


    /**
     * Checks adjacent nodes to the current node and updates the open list.
     */
    private void checkAdjacentNodes(int col, int row){
        if (row > 0) open(nodes[col][row-1]);
        if (col > 0) open(nodes[col-1][row]);
        if (row < panel.getTilePainter().getMapHeight() - 1) open(nodes[col][row+1]);
        if (col < panel.getTilePainter().getMapWidth() - 1) open(nodes[col+1][row]);
    }
    /**
     * Opens a node if it is not already open, closed, or solid.
     *
     * @param node the node to be opened
     */
    private void open(Node node){
        if (!node.isOpen() && !node.isClosed() && !node.isSolid()){
            node.setOpen(true);
            node.setParent(current);
            openList.add(node);
        }
    }
    /**
     * Finds the path from the current node to the start node using Stack as data storage.
     */
    public void findPath(){
        Node temp = current;
        while (temp != start){
            path.push(temp);
            temp = temp.getParent();

        }
    }
    public Stack<Node> getPath() {
        return path;
    }
    public boolean isPathPossible() {
        return pathPossible;
    }
}
