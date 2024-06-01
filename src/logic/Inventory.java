package logic;

import items.Item;

import java.util.HashMap;
public class Inventory {
    int selectedCol;
    int selectedRow;

    private HashMap<Item, Integer> items = new HashMap<>();

    /**
     * Adds an item to inventory.
     *
     * @param item the item to be added
     */
    public void addToInventory(Item item) {
        if (item != null) {
            if (items.get(item) == null) {
                items.put(item, 1);
            } else {
                int count = items.get(item);
                items.put(item, count + 1);
            }
        }
    }

    /**
     * Removes an item from inventory.
     *
     * @param item the item to be removed
     */
    public void removeItem(Item item){
        int counter = items.get(item);
        if (counter - 1 == 0){
            items.put(item, 0);
        }
        else {
            items.put(item, counter - 1);
        }
    }


    /**
     * Adds row in an imaginary graphical inventory.
     */
    public void addRow() {
        if (selectedRow < 1) {
            selectedRow++;
        } else {
            selectedRow = 0;
        }
    }

    /**
     * Subtracts row in an imaginary graphical inventory.
     */
    public void subtractRow() {
        if (selectedRow > 0) {
            selectedRow--;
        } else {
            selectedRow = 1;
        }
    }

    /**
     * Adds column in an imaginary graphical inventory.
     */
    public void addCol() {
        if (selectedCol < 4) {
            selectedCol++;
        } else {
            selectedCol = 0;
            addRow();
        }
    }

    /**
     * Subtracts column in an imaginary graphical inventory.
     */
    public void subtractCol() {
        if (selectedCol > 0) {
            selectedCol--;
        } else {
            selectedCol = 4;
            subtractRow();
        }
    }
    public int getSelectedCol() {
        return selectedCol;
    }
    public int getSelectedRow() {
        return selectedRow;
    }
    public HashMap<Item, Integer> getItems() {
        return items;
    }

}
