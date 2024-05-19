package pathFinding;

import entities.Entity;
import world.ApplicationPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class Search {

    private ApplicationPanel panel;
    private Node[][] nodes;
    private ArrayList<Node> openList = new ArrayList<>();
    private Stack<Node> path = new Stack<>();
    private Node current, start, goal;
    private boolean finished;
    private int numOfAttempts = 500;
    private boolean pathPossible = false;

    public Search(ApplicationPanel panel) {
        this.panel = panel;
    }

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


    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        initialize();
        start = nodes[startCol][startRow];
        current = start;
        goal = nodes[goalCol][goalRow];
        openList.add(start);

        for (int i = 0; i < panel.getTilePainter().getMapHeight(); i++) {
            for (int j = 0; j < panel.getTilePainter().getMapWidth(); j++) {
                if (panel.getTilePainter().isTileSolid(j,i)){
                    nodes[j][i].setSolid(true);
                }

                setDistanceValues(nodes[j][i]);
            }
        }
    }


    public void setDistanceValues(Node node){
        node.setG(Math.abs(node.getCol() - start.getCol()) + Math.abs(node.getRow() - start.getRow()));
        node.setH(Math.abs(node.getCol() - goal.getCol()) + Math.abs(node.getRow() - goal.getRow()));

        node.setF(node.getG() + node.getH());
    }

    public void checkNodes(){
        int col;
        int row;
        int bestIndex;
        int minFValue;
        pathPossible = false;
        finished = false;
        numOfAttempts = 500;
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

    private void checkAdjacentNodes(int col, int row){
        if (row > 0) open(nodes[col][row-1]);
        if (col > 0) open(nodes[col-1][row]);
        if (row < panel.getTilePainter().getMapHeight() - 1) open(nodes[col][row+1]);
        if (col < panel.getTilePainter().getMapWidth() - 1) open(nodes[col+1][row]);
    }

    private void open(Node node){
        if (!node.isOpen() && !node.isClosed() && !node.isSolid()){
            node.setOpen(true);
            node.setParent(current);
            openList.add(node);
        }
    }

    public void findPath(){
        Node temp = current;
        BufferedImage image;
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
