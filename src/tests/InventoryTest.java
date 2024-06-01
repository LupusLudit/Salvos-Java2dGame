package tests;
import items.Bandage;
import items.Pistol;
import logic.ApplicationPanel;
import logic.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    ApplicationPanel panel;
    Inventory inventory;
    Pistol pistol;
    Bandage bandage;
    @BeforeEach
    void setUp() {
        panel = new ApplicationPanel();
        bandage = new Bandage(panel);
        pistol = new Pistol(panel);
        inventory = new Inventory();
    }

    @Test
    void addToInventory() {
        inventory.addToInventory(bandage);
        inventory.addToInventory(bandage);
        inventory.addToInventory(pistol);

        assertEquals(2, inventory.getItems().get(bandage));
        assertEquals(1, inventory.getItems().get(pistol));
        assertEquals(2, inventory.getItems().size());
    }

    @Test
    void removeItem() {
        inventory.addToInventory(bandage);
        inventory.addToInventory(pistol);
        inventory.addToInventory(pistol);
        inventory.removeItem(bandage);
        inventory.removeItem(pistol);

        assertEquals(0, inventory.getItems().get(bandage));
        assertEquals(1, inventory.getItems().get(pistol));
    }

    @Test
    void addRow() {
        inventory.addRow();
        assertEquals(1, inventory.getSelectedRow());
        inventory.addRow();
        assertEquals(0, inventory.getSelectedRow());
    }

    @Test
    void subtractRow() {
        inventory.subtractRow();
        assertEquals(1, inventory.getSelectedRow());
        inventory.subtractRow();
        assertEquals(0, inventory.getSelectedRow());
    }

    @Test
    void addCol() {
        inventory.addCol();
        inventory.addCol();
        assertEquals(2, inventory.getSelectedCol());
        inventory.addCol();
        assertEquals(3, inventory.getSelectedCol());
    }

    @Test
    void subtractCol() {
        inventory.subtractCol();
        assertEquals(4, inventory.getSelectedCol());
        inventory.subtractCol();
        assertEquals(3, inventory.getSelectedCol());
    }
}