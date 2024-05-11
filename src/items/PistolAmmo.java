package items;

import world.AmmoType;
import world.Panel;

public class PistolAmmo extends Item{

    public PistolAmmo(Panel panel) {
        super(panel);
        setImage("/ui/pistol_ammo.png");
        setPrice(15);
    }

    @Override
    public void use() {
    }

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(AmmoType.PISTOL).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 9;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(AmmoType.PISTOL, text);
    }
}
