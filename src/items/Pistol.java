package items;

import logic.Weapon;
import logic.ApplicationPanel;

public class Pistol extends Item{

    public Pistol(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/pistol.png");
        setPrice(200);
    }

    @Override
    public void use() {
        applicationPanel.getGame().setSelectedWeapon(Weapon.PISTOL);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
