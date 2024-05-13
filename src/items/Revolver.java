package items;

import world.AmmoType;
import world.Panel;

public class Revolver extends Item {

    public Revolver(Panel panel) {
        super(panel);
        setImage("/ui/revolver.png");
        setPrice(1);
    }
    @Override
    public void use() {
        panel.getGame().setSelectedAmmo(AmmoType.REVOLVER);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
