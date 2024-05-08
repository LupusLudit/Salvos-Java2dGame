package items;

import world.Panel;
import world.Weapons;

public class SemiAutoRifle extends Item{

    public SemiAutoRifle(Panel panel) {
        super(panel);
        setImage("/ui/semi-auto.png");
    }

    @Override
    public void use() {
        panel.getGame().setCurrentWeapon(Weapons.SEMIAUTO);
    }
}
