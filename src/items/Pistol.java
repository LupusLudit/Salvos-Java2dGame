package items;

import world.Panel;
import world.Weapons;

public class Pistol extends Item{

    public Pistol(Panel panel) {
        super(panel);
        setImage("/ui/pistol.png");
    }

    @Override
    public void use() {
        panel.getGame().setCurrentWeapon(Weapons.PISTOL);
    }
}
