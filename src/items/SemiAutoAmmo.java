package items;

import world.AmmoType;
import world.ApplicationPanel;

public class SemiAutoAmmo extends Item{

    public SemiAutoAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/grant_ammo.png");
        setPrice(1);
    }

    @Override
    public void use() {
    }

    @Override
    public void collect() {
        String[] arr = applicationPanel.getGame().getAmmoMap().get(AmmoType.SEMIAUTO).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 10;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(AmmoType.SEMIAUTO, text);
    }
}
