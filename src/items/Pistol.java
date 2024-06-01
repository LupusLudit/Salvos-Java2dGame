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
        panel.getGame().setSelectedWeapon(Weapon.PISTOL);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
