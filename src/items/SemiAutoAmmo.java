package items;

import logic.Weapon;
import logic.ApplicationPanel;
public class SemiAutoAmmo extends Item{

    /**
     * Semi auto ammo constructor.
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
        String[] arr = panel.getGame().getAmmoMap().get(Weapon.SEMIAUTO).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 30;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(Weapon.SEMIAUTO, text);
    }
}
