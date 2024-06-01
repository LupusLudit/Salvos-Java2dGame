package tests;

import items.AssaultRifle;
import items.Pistol;
import logic.ApplicationPanel;
import logic.Inventory;
import logic.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssaultRifleTest {

    ApplicationPanel panel;
    Inventory inventory;
    AssaultRifle assaultRifle;

    @BeforeEach
    void setUp() {
        panel = new ApplicationPanel();
        inventory = new Inventory();
        assaultRifle = new AssaultRifle(panel);
        panel.getPlayer().setInventory(inventory); //so the revolver isn't automatically added
    }
    @Test
    void use() {
        assaultRifle.use();
        assertEquals(Weapon.ASSAULTRIFLE, panel.getGame().getSelectedWeapon());
    }

    @Test
    void collect() {
        assaultRifle.collect();
        assaultRifle.collect();
        assaultRifle.collect();
        assertEquals(3, panel.getPlayer().getInventory().getItems().get(assaultRifle));
        assaultRifle.collect();
        assertEquals(1, panel.getPlayer().getInventory().getItems().size());
    }
}