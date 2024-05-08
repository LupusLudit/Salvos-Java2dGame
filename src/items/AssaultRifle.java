package items;

import world.Panel;
import world.Weapons;

public class AssaultRifle extends Item{

    public AssaultRifle(Panel panel) {
        super(panel);
        setImage("/ui/assault-rifle.png");
    }

    @Override
    public void use() {
        panel.getGame().setCurrentWeapon(Weapons.ASSAULTRIFLE);
    }
}
