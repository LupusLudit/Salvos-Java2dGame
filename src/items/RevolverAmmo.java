package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Revolver ammo.
 */
public class RevolverAmmo extends Item{


    /**
     * Instantiates a new Revolver ammo.
     *
     * @param applicationPanel the application panel
     */
    public RevolverAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/revolverAmmo.png");
        setPrice(25);
    }
    @Override
    public void use() {}

    @Override
    public void collect() {
        String[] arr = applicationPanel.getGame().getAmmoMap().get(Weapon.REVOLVER).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 18;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(Weapon.REVOLVER, text);
    }
}
