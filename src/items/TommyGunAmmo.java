package items;

import logic.Weapon;
import logic.ApplicationPanel;
public class TommyGunAmmo extends Item{

    /**
     * Tommy gun ammo constructor.
     *
     * @param applicationPanel the application panel
     */
    public TommyGunAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/tommyGunAmmo.png");
        setPrice(250);
    }
    @Override
    public void use() {}

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(Weapon.SUBMACHINE_GUN).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 150;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(Weapon.SUBMACHINE_GUN, text);
    }
}
