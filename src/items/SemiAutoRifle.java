package items;

import logic.Weapon;
import logic.ApplicationPanel;
public class SemiAutoRifle extends Item{
    public SemiAutoRifle(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/semi-auto.png");
        setPrice(500);
    }

    @Override
    public void use() {
        panel.getGame().setSelectedWeapon(Weapon.SEMIAUTO);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
