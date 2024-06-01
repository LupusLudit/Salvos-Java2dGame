package items;

import logic.Weapon;
import logic.ApplicationPanel;

public class RevolverAmmo extends Item{
    public RevolverAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/revolverAmmo.png");
        setPrice(25);
    }
    @Override
    public void use() {}

    @Override
    public void collect() {
        String[] arr = panel.getGame().getAmmoMap().get(Weapon.REVOLVER).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 18;

        String text = arr[0] + "," + ammo + "," + arr[2];
        panel.getGame().getAmmoMap().put(Weapon.REVOLVER, text);
    }
}
