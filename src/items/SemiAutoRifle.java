package items;

import world.Weapon;
import world.ApplicationPanel;

public class SemiAutoRifle extends Item{

    public SemiAutoRifle(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/semi-auto.png");
        setPrice(500);
    }

    @Override
    public void use() {
        applicationPanel.getGame().setSelectedWeapon(Weapon.SEMIAUTO);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
