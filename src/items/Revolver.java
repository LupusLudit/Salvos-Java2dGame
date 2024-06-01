package items;

import logic.Weapon;
import logic.ApplicationPanel;
public class Revolver extends Item {
    public Revolver(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/revolver.png");
        setPrice(50);
    }
    @Override
    public void use() {
        panel.getGame().setSelectedWeapon(Weapon.REVOLVER);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
