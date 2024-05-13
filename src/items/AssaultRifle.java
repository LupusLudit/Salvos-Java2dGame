package items;

import world.AmmoType;
import world.Panel;

public class AssaultRifle extends Item{

    public AssaultRifle(Panel panel) {
        super(panel);
        setImage("/ui/assault-rifle.png");
        setPrice(1);
    }

    @Override
    public void use() {
        panel.getGame().setSelectedAmmo(AmmoType.ASSAULTRIFLE);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
