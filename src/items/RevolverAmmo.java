package items;

import world.AmmoType;
import world.Panel;

public class RevolverAmmo extends Item{


    public RevolverAmmo(Panel panel) {
        super(panel);
        setImage("/ui/revolverAmmo.png");
        setPrice(1);
    }
    @Override
    public void use() {}

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(AmmoType.REVOLVER).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 18;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(AmmoType.REVOLVER, text);
    }
}
