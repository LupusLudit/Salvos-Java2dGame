package items;

import world.ApplicationPanel;

public class EnergyDrink extends Item{

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
