package items;

import logic.Weapon;
import logic.ApplicationPanel;
public class TommyGun extends Item{
    /**
     * Tommy gun constructor.
     *
     * @param applicationPanel the application panel
     */
    public TommyGun(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/tommyGun.png");
        setPrice(2000);
    }

    @Override
    public void use() {
        panel.getGame().setSelectedWeapon(Weapon.SUBMACHINE_GUN);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
