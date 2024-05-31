package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Pistol ammo.
 */
public class PistolAmmo extends Item{

    /**
     * Instantiates a new Pistol ammo.
     *
     * @param applicationPanel the application panel
     */
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
        String[] arr = applicationPanel.getGame().getAmmoMap().get(Weapon.PISTOL).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 27;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(Weapon.PISTOL, text);
    }
}
