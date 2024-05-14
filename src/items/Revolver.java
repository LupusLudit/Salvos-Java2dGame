package items;

import world.AmmoType;
import world.ApplicationPanel;

public class Revolver extends Item {

    public Revolver(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/revolver.png");
        setPrice(1);
    }
    @Override
    public void use() {
        applicationPanel.getGame().setSelectedAmmo(AmmoType.REVOLVER);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
