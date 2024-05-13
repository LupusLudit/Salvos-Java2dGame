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
        int counter = panel.getPlayer().getInventory().getItems().get(this);
        panel.getPlayer().getInventory().getItems().put(this, counter - 1);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
