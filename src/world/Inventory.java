package world;

import items.Item;

import java.util.HashMap;

public class Inventory {


    int selectedCol;
    int selectedRow;

    private HashMap<Item, Integer> items = new HashMap<>();

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

    public void removeItem(Item item){
        int counter = items.get(item);
        if (counter - 1 == 0){
            items.put(item, 0);
        }
        else {
            items.put(item, counter - 1);
        }
    }


    public void addRow() {
        if (selectedRow < 1) {
            selectedRow++;
        } else {
            selectedRow = 0;
        }
    }

    public void subtractRow() {
        if (selectedRow > 0) {
            selectedRow--;
        } else {
            selectedRow = 1;
        }
    }

    public void addCol() {
        if (selectedCol < 4) {
            selectedCol++;
        } else {
            selectedCol = 0;
            addRow();
        }
    }

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
