package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Assault rifle ammo.
 */
public class AssaultRifleAmmo extends Item{

    /**
     * Instantiates a new Assault rifle ammo.
     *
     * @param applicationPanel the application panel
     */
    public AssaultRifleAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/akAmmo.png");
        setPrice(150);
    }

    @Override
    public void use() {}

    @Override
    public void collect() {
        String[] arr = applicationPanel.getGame().getAmmoMap().get(Weapon.ASSAULTRIFLE).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 90;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(Weapon.ASSAULTRIFLE, text);
    }
}
