package items;

import world.AmmoType;
import world.Panel;

public class RifleAmmo extends Item{

    public RifleAmmo(Panel panel) {
        super(panel);
        setImage("/ui/ak_ammo.png");
    }

    @Override
    public void use() {

    }

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(AmmoType.ASSAULTRIFLE).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 30;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(AmmoType.ASSAULTRIFLE, text);
    }
}
