package items;

import world.AmmoType;
import world.Panel;

public class SemiAutoAmmo extends Item{

    public SemiAutoAmmo(Panel panel) {
        super(panel);
        setImage("/ui/grant_ammo.png");
        setPrice(1);
    }

    @Override
    public void use() {
    }

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(AmmoType.SEMIAUTO).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 10;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(AmmoType.SEMIAUTO, text);
    }
}
