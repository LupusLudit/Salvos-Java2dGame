package items;

import world.AmmoType;
import world.ApplicationPanel;

public class Pistol extends Item{

    public Pistol(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/pistol.png");
        setPrice(1);
    }

    @Override
    public void use() {
        applicationPanel.getGame().setSelectedAmmo(AmmoType.PISTOL);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
