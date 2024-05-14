package collectables;

import items.*;
import world.ApplicationPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CollectableManager {


    ArrayList<Collectable> collectables = new ArrayList<>();
    ApplicationPanel applicationPanel;


    public CollectableManager(ApplicationPanel applicationPanel) {
        this.applicationPanel = applicationPanel;

        Bandage bandage = new Bandage(applicationPanel);
        EnergyDrink energyDrink = new EnergyDrink(applicationPanel);
        PistolAmmo pistolAmmo = new PistolAmmo(applicationPanel);
        SemiAutoAmmo semiAutoAmmo = new SemiAutoAmmo(applicationPanel);
        RifleAmmo rifleAmmo = new RifleAmmo(applicationPanel);


        addCollectables(new Collectable(applicationPanel, bandage, 40, 40));
        addCollectables(new Collectable(applicationPanel, bandage, 39, 38));
        addCollectables(new Collectable(applicationPanel, energyDrink, 35, 35));
        addCollectables(new Collectable(applicationPanel, rifleAmmo, 30, 30));
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
            if (collectable.intersectsPlayer() && applicationPanel.getPlayer().getInventory().getItems().size() < 6) {
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
