package items;

import world.Weapon;
import world.ApplicationPanel;

public class SemiAutoAmmo extends Item{

    public SemiAutoAmmo(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/grant_ammo.png");
        setPrice(1);
    }

    @Override
    public void use() {
    }

    @Override
    public void collect() {
        String[] arr = applicationPanel.getGame().getAmmoMap().get(Weapon.SEMIAUTO).split(",");

        int ammo = Integer.parseInt(arr[1]);
        ammo += 10;

        String text = arr[0] + "," + ammo + "," + arr[2];
        applicationPanel.getGame().getAmmoMap().put(Weapon.SEMIAUTO, text);
    }
}
