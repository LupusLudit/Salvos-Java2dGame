package items;

import logic.ApplicationPanel;

/**
 * The type Energy drink.
 */
public class EnergyDrink extends Item{

    /**
     * Instantiates a new Energy drink.
     *
     * @param applicationPanel the application panel
     */
    public EnergyDrink(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/energyDrink.png");
    }

    @Override
    public void use() {
        applicationPanel.getPlayer().addStamina(30);
        applicationPanel.getPlayer().getInventory().removeItem(this);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
