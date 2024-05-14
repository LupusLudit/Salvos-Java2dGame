package items;

import world.AmmoType;
import world.ApplicationPanel;

public class TommyGunAmmo extends Item{

    public TommyGunAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/ui/tommyGunAmmo.png");
        setPrice(1);
    }
    @Override
    public void use() {}

    @Override
    public void collect() {
        String[] arr = applicationPanel.getGame().getAmmoMap().get(AmmoType.SUBMACHINE_GUN).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 50;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(AmmoType.SUBMACHINE_GUN, text);
    }
}
