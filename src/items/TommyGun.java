package items;

import world.AmmoType;
import world.Panel;

public class TommyGun extends Item{
    public TommyGun(Panel panel) {
        super(panel);
        setImage("/ui/tommyGun.png");
        setPrice(1);
    }

    @Override
    public void use() {
        panel.getGame().setSelectedAmmo(AmmoType.SUBMACHINE_GUN);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
