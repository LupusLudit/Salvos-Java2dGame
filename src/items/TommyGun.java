package items;

import world.Weapon;
import world.ApplicationPanel;

public class TommyGun extends Item{
    public TommyGun(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/tommyGun.png");
        setPrice(1);
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
