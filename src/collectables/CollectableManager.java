package collectables;

import items.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CollectableManager {


    ArrayList<Collectable> collectables = new ArrayList<>();
    world.Panel panel;


    public CollectableManager(world.Panel panel) {
        this.panel = panel;

        Bandage bandage = new Bandage(panel);
        EnergyDrink energyDrink = new EnergyDrink(panel);
        PistolAmmo pistolAmmo = new PistolAmmo(panel);
        SemiAutoAmmo semiAutoAmmo = new SemiAutoAmmo(panel);
        RifleAmmo rifleAmmo = new RifleAmmo(panel);


        addCollectables(new Collectable(panel, bandage, 40, 40));
        addCollectables(new Collectable(panel, bandage, 39, 38));
        addCollectables(new Collectable(panel, energyDrink, 35, 35));
        addCollectables(new Collectable(panel, rifleAmmo, 30, 30));
    }

    public void addCollectables(Collectable collectable){
        if(collectable != null){
            collectables.add(collectable);
        }
    }

    public void checkCollectables() {
        Iterator<Collectable> iterator = collectables.iterator();
        while (iterator.hasNext()) {
            Collectable collectable = iterator.next();
            if (collectable.intersectsPlayer() && panel.getPlayer().getInventory().size() < 7) {
                collectable.getItem().collect();
                iterator.remove();
            }
        }
    }

    public void drawCollectables(Graphics2D g) {
        for (Collectable collectable : collectables) {
            if (collectable != null) {
                collectable.draw(g);
            }
        }
    }
}
