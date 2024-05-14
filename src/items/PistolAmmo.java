package items;

import world.AmmoType;
import world.ApplicationPanel;

public class PistolAmmo extends Item{

    public PistolAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/pistol_ammo.png");
        setPrice(1);
    }

    @Override
    public void use() {
    }

    @Override
    public void collect() {
        String[] arr = applicationPanel.getGame().getAmmoMap().get(AmmoType.PISTOL).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 9;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(AmmoType.PISTOL, text);
    }
}
