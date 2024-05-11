package items;

import world.Panel;

public class Bandage extends Item{

    public Bandage(Panel panel) {
        super(panel);
        setImage("/ui/bandage.png");
    }

    @Override
    public void use() {
        for (int i = 0; i < 5; i++){
            panel.getPlayer().increaseLives();
        }

        int counter = panel.getPlayer().getInventory().get(this);
        panel.getPlayer().getInventory().put(this, counter - 1);
    }

    @Override
    public void collect() {
        panel.getPlayer().addToInventory(this);
    }
}
