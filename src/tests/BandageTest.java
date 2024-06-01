package tests;

import entities.Player;
import items.Bandage;
import items.Revolver;
import logic.ApplicationPanel;
import logic.Inventory;
import logic.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BandageTest {

    Bandage bandage;
    ApplicationPanel panel;
    Inventory inventory;
    @BeforeEach
    void setUp() {
        panel = new ApplicationPanel();
        bandage = new Bandage(panel);
        inventory = new Inventory();
        panel.getPlayer().setInventory(inventory); //so the revolver isn't automatically added
    }
    @Test
    void use() {
        panel.getPlayer().getInventory().addToInventory(bandage);
        for (int i = 0; i < 5; i++){
            panel.getPlayer().decreaseLives();
        }
        bandage.use();
        assertEquals(30, panel.getPlayer().getLives());
        assertEquals(0, panel.getPlayer().getInventory().getItems().get(bandage));
    }

    @Test
    void collect() {
        bandage.collect();
        bandage.collect();
        bandage.collect();
        assertEquals(3, panel.getPlayer().getInventory().getItems().get(bandage));
        bandage.collect();
        assertEquals(1, panel.getPlayer().getInventory().getItems().size());
    }
}