package items;

import logic.Weapon;
import logic.ApplicationPanel;

public class AssaultRifleAmmo extends Item{

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
        ammo += 30;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(Weapon.ASSAULTRIFLE, text);
    }
}
