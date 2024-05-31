package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Revolver.
 */
public class Revolver extends Item {

    /**
     * Instantiates a new Revolver.
     *
     * @param applicationPanel the application panel
     */
    public Revolver(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/revolver.png");
        setPrice(50);
    }
    @Override
    public void use() {
        applicationPanel.getGame().setSelectedWeapon(Weapon.REVOLVER);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
