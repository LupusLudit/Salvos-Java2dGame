package items;

import world.Weapon;
import world.ApplicationPanel;

public class AssaultRifle extends Item{

    public AssaultRifle(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/assault-rifle.png");
        setPrice(1);
    }

    @Override
    public void use() {
        applicationPanel.getGame().setSelectedWeapon(Weapon.ASSAULTRIFLE);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
