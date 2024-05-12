package world;

import items.*;

public class Shop {

    int selectedCol;
    int selectedRow;
    Item[][] items = new Item[5][2];
    world.Panel panel;


    public Shop(world.Panel panel){
        this.panel = panel;

        items[0][0] = new Revolver(panel);
        items[1][0] = new Pistol(panel);
        items[2][0] = new SemiAutoRifle(panel);
        items[3][0] = new AssaultRifle(panel);
        items[4][0] = new TommyGun(panel);

        items[0][1] = new RevolverAmmo(panel);
        items[1][1] = new PistolAmmo(panel);
        items[2][1] = new SemiAutoAmmo(panel);
        items[3][1] = new RifleAmmo(panel);
        items[4][1] = new TommyGunAmmo(panel);
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

    public items.Item getSelectedItem() {
        return items[selectedCol][selectedRow];
    }

    public int getSelectedCol() {
        return selectedCol;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public Item getItem(int col, int row){
        return items[col][row];
    }
}
