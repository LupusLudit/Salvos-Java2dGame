package tests;

import logic.ApplicationPanel;
import logic.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    Shop shop;
    ApplicationPanel panel;
    @BeforeEach
    void setUp() {
        panel = new ApplicationPanel();
        shop = new Shop(panel);
    }

    @Test
    void addRow() {
        shop.addRow();
        assertEquals(1, shop.getSelectedRow());
        shop.addRow();
        assertEquals(0, shop.getSelectedRow());
    }

    @Test
    void subtractRow() {
        shop.subtractRow();
        assertEquals(1, shop.getSelectedRow());
        shop.subtractRow();
        assertEquals(0, shop.getSelectedRow());
    }

    @Test
    void addCol() {
        shop.addCol();
        assertEquals(1, shop.getSelectedCol());
        shop.addCol();
        shop.addCol();
        assertEquals(3, shop.getSelectedCol());
    }

    @Test
    void subtractCol() {
        shop.subtractCol();
        assertEquals(4, shop.getSelectedCol());
        shop.subtractCol();
        assertEquals(3, shop.getSelectedCol());
    }
}