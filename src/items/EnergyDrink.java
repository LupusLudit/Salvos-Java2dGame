package items;

import world.Panel;

public class EnergyDrink extends Item{

    public EnergyDrink(Panel panel) {
        super(panel);
        setImage("/ui/energy_drink.png");
    }

    @Override
    public void use() {
        panel.getPlayer().addStamina(30);
        int counter = panel.getPlayer().getInventory().get(this);
        panel.getPlayer().getInventory().put(this, counter - 1);
    }

    @Override
    public void collect() {
        panel.getPlayer().addToInventory(this);
    }
}
