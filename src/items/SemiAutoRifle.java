package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Semi auto rifle.
 */
public class SemiAutoRifle extends Item{

    /**
     * Instantiates a new Semi auto rifle.
     *
     * @param applicationPanel the application panel
     */
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
