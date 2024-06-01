package items;

import logic.ApplicationPanel;

public class EnergyDrink extends Item{
    public EnergyDrink(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/energyDrink.png");
    }

    @Override
    public void use() {
        panel.getPlayer().startClock(30);
        panel.getPlayer().getInventory().removeItem(this);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
