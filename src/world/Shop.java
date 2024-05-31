package world;

import items.*;

public class Shop {

    int selectedCol;
    int selectedRow;
    Item[][] items = new Item[5][2];
    ApplicationPanel applicationPanel;


    public Shop(ApplicationPanel applicationPanel){
        this.applicationPanel = applicationPanel;

        items[0][0] = new Revolver(applicationPanel);
        items[1][0] = new Pistol(applicationPanel);
        items[2][0] = new SemiAutoRifle(applicationPanel);
        items[3][0] = new AssaultRifle(applicationPanel);
        items[4][0] = new TommyGun(applicationPanel);

        items[0][1] = new RevolverAmmo(applicationPanel);
        items[1][1] = new PistolAmmo(applicationPanel);
        items[2][1] = new SemiAutoAmmo(applicationPanel);
        items[3][1] = new AssaultRifleAmmo(applicationPanel);
        items[4][1] = new TommyGunAmmo(applicationPanel);
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
