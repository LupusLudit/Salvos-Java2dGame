package logic;

import items.*;

/**
 * The type Shop.
 */
public class Shop {

    /**
     * The Selected col.
     */
    int selectedCol;
    /**
     * The Selected row.
     */
    int selectedRow;
    /**
     * The Items.
     */
    Item[][] items = new Item[5][2];
    /**
     * The Application panel.
     */
    ApplicationPanel applicationPanel;


    /**
     * Instantiates a new Shop.
     *
     * @param applicationPanel the application panel
     */
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
     * Gets selected item.
     *
     * @return the selected item
     */
    public items.Item getSelectedItem() {
        return items[selectedCol][selectedRow];
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
     * Get item item.
     *
     * @param col the col
     * @param row the row
     * @return the item
     */
    public Item getItem(int col, int row){
        return items[col][row];
    }
}
