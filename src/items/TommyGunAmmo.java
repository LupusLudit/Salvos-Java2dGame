package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Tommy gun ammo.
 */
public class TommyGunAmmo extends Item{

    /**
     * Instantiates a new Tommy gun ammo.
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
        String[] arr = applicationPanel.getGame().getAmmoMap().get(Weapon.SUBMACHINE_GUN).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 150;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(Weapon.SUBMACHINE_GUN, text);
    }
}
