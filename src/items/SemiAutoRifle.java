package items;

import world.AmmoType;
import world.Panel;

public class SemiAutoRifle extends Item{

    public SemiAutoRifle(Panel panel) {
        super(panel);
        setImage("/ui/semi-auto.png");
        setPrice(1);
    }

    @Override
    public void use() {
        panel.getGame().setSelectedAmmo(AmmoType.SEMIAUTO);
    }

    @Override
    public void collect() {
        panel.getPlayer().getInventory().addToInventory(this);
    }
}
