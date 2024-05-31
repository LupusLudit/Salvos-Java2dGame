package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Tommy gun.
 */
public class TommyGun extends Item{
    /**
     * Instantiates a new Tommy gun.
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
        applicationPanel.getGame().setSelectedWeapon(Weapon.SUBMACHINE_GUN);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
