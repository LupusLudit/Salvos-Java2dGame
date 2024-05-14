package items;

import world.AmmoType;
import world.ApplicationPanel;

public class SemiAutoRifle extends Item{

    public SemiAutoRifle(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/semi-auto.png");
        setPrice(1);
    }

    @Override
    public void use() {
        applicationPanel.getGame().setSelectedAmmo(AmmoType.SEMIAUTO);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
