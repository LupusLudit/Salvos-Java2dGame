package logic;

import items.Item;

import java.util.HashMap;

/**
 * The type Inventory.
 */
public class Inventory {


    /**
     * The Selected col.
     */
    int selectedCol;
    /**
     * The Selected row.
     */
    int selectedRow;

    private HashMap<Item, Integer> items = new HashMap<>();

    /**
     * Add to inventory.
     *
     * @param item the item
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
     * Remove item.
     *
     * @param item the item
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
     * Add row.
     */
    public void addRow() {
        if (selectedRow < 1) {
            selectedRow++;
        } else {
            selectedRow = 0;
        }
    }

    /**
     * Subtract row.
     */
    public void subtractRow() {
        if (selectedRow > 0) {
            selectedRow--;
        } else {
            selectedRow = 1;
        }
    }

    /**
     * Add col.
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
     * Subtract col.
     */
    public void subtractCol() {
        if (selectedCol > 0) {
            selectedCol--;
        } else {
            selectedCol = 4;
            subtractRow();
        }
    }

    /**
     * Gets selected col.
     *
     * @return the selected col
     */
    public int getSelectedCol() {
        return selectedCol;
    }

    /**
     * Gets selected row.
     *
     * @return the selected row
     */
    public int getSelectedRow() {
        return selectedRow;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public HashMap<Item, Integer> getItems() {
        return items;
    }

}
