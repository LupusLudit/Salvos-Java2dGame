package items;

import world.ApplicationPanel;

public class Bandage extends Item{

    public Bandage(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/bandage.png");
    }

    @Override
    public void use() {
        for (int i = 0; i < 5; i++){
            applicationPanel.getPlayer().increaseLives();
        }
        applicationPanel.getPlayer().getInventory().removeItem(this);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
