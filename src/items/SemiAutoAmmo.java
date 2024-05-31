package items;

import logic.Weapon;
import logic.ApplicationPanel;

/**
 * The type Semi auto ammo.
 */
public class SemiAutoAmmo extends Item{

    /**
     * Instantiates a new Semi auto ammo.
     *
     * @param applicationPanel the application panel
     */
    public SemiAutoAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/grant_ammo.png");
        setPrice(75);
    }

    @Override
    public void use() {
    }

    @Override
    public void collect() {
        String[] arr = applicationPanel.getGame().getAmmoMap().get(Weapon.SEMIAUTO).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 30;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(Weapon.SEMIAUTO, text);
    }
}
