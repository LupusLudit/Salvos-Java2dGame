package items;

import logic.Weapon;
import logic.ApplicationPanel;

public class PistolAmmo extends Item{
    public PistolAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/pistol_ammo.png");
        setPrice(50);
    }

    @Override
    public void use() {
    }

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(Weapon.PISTOL).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 27;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(Weapon.PISTOL, text);
    }
}
