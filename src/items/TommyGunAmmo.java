package items;

import world.AmmoType;
import world.Panel;

public class TommyGunAmmo extends Item{

    public TommyGunAmmo(Panel panel) {
        super(panel);
        setImage("/ui/tommyGunAmmo.png");
        setPrice(1);
    }
    @Override
    public void use() {}

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(AmmoType.SUBMACHINE_GUN).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 50;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(AmmoType.SUBMACHINE_GUN, text);
    }
}
