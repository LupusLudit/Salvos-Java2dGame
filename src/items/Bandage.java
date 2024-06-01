package items;

import logic.ApplicationPanel;

public class Bandage extends Item{
    public Bandage(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/bandage.png");
    }

    @Override
    public void use() {
        for (int i = 0; i < 5; i++){
            panel.getPlayer().increaseLives();
        }
        panel.getPlayer().getInventory().removeItem(this);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
