package items;

import world.AmmoType;
import world.Panel;

public class Pistol extends Item{

    public Pistol(Panel panel) {
        super(panel);
        setImage("/ui/pistol.png");
    }

    @Override
    public void use() {
        panel.getGame().setSelectedAmmo(AmmoType.PISTOL);
    }

    @Override
    public void collect() {
        panel.getPlayer().addToInventory(this);
    }
}
