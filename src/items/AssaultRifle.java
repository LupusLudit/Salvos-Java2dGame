package items;

import logic.Weapon;
import logic.ApplicationPanel;

public class AssaultRifle extends Item{

    /**
     * Assault rifle constructor.
     *
     * @param applicationPanel the application panel
     */
    public AssaultRifle(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/assault-rifle.png");
        setPrice(1250);
    }

    @Override
    public void use() {
        panel.getGame().setSelectedWeapon(Weapon.ASSAULTRIFLE);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
