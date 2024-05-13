package items;

import world.AmmoType;
import world.Panel;

public class Pistol extends Item{

    public Pistol(Panel panel) {
        super(panel);
        setImage("/ui/pistol.png");
        setPrice(1);
    }

    @Override
    public void use() {
        panel.getGame().setSelectedAmmo(AmmoType.PISTOL);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
