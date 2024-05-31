package items;

import world.Weapon;
import world.ApplicationPanel;

public class TommyGun extends Item{
    public TommyGun(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/tommyGun.png");
        setPrice(2000);
    }

    @Override
    public void use() {
        applicationPanel.getGame().setSelectedWeapon(Weapon.SUBMACHINE_GUN);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
