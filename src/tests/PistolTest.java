package tests;

import items.Pistol;
import logic.ApplicationPanel;
import logic.Inventory;
import logic.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PistolTest {
    ApplicationPanel panel;
    Inventory inventory;
    Pistol pistol;

    @BeforeEach
    void setUp() {
        panel = new ApplicationPanel();
        inventory = new Inventory();
        pistol = new Pistol(panel);
        panel.getPlayer().setInventory(inventory); //so the revolver isn't automatically added
    }
    @Test
    void use() {
        panel.getPlayer().getInventory().addToInventory(pistol);
        pistol.use();

        assertEquals(Weapon.PISTOL, panel.getGame().getSelectedWeapon());
        assertEquals(1, panel.getPlayer().getInventory().getItems().get(pistol));
    }

    @Test
    void collect() {
        pistol.collect();
        assertEquals(1, panel.getPlayer().getInventory().getItems().get(pistol));
        pistol.collect();
        pistol.collect();
        assertEquals(1, panel.getPlayer().getInventory().getItems().size());
    }
}