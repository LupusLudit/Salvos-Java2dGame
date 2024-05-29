package collectables;

import items.*;
import world.ApplicationPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CollectableManager {


    ArrayList<Collectable> collectables = new ArrayList<>();
    ApplicationPanel panel;
    Bandage bandage;
    EnergyDrink energyDrink;
    RevolverAmmo revolverAmmo;
    PistolAmmo pistolAmmo;
    SemiAutoAmmo semiAutoAmmo;
    RifleAmmo rifleAmmo;
    TommyGunAmmo tommyGunAmmo;

    public CollectableManager(ApplicationPanel panel) {
        this.panel = panel;
        instantiate();
    }

    /**
     * This method instantiate collectables. It is necessary to store the same items in memory, so the program works properly and is more memory safe.
     */
    private void instantiate(){
        bandage = new Bandage(panel);
        energyDrink = new EnergyDrink(panel);
        revolverAmmo = new RevolverAmmo(panel);
        pistolAmmo = new PistolAmmo(panel);
        semiAutoAmmo = new SemiAutoAmmo(panel);
        rifleAmmo = new RifleAmmo(panel);
        tommyGunAmmo = new TommyGunAmmo(panel);
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
            if (collectable.intersectsPlayer() && panel.getPlayer().getInventory().getItems().size() < 6) {
                collectable.getItem().collect();
                panel.getEffectManager().addPickUpEffects();
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

    public void addRandomCollectables(){
        Random rn = new Random();
        int col;
        int row;
        int chosen;
        for (int i = 0; i < 7; i++){
            col = rn.nextInt(20)+70;
            row = rn.nextInt(17)+80;
            chosen = rn.nextInt(7);

        if (!panel.getTilePainter().getTile(col,row).isSolid()){
            switch (chosen){
                case 0 -> addCollectables(new Collectable(panel, bandage, col,row));
                case 1 -> addCollectables(new Collectable(panel, energyDrink, col,row));
                case 2 -> addCollectables(new Collectable(panel, revolverAmmo, col,row));
                case 3 -> addCollectables(new Collectable(panel, pistolAmmo, col,row));
                case 4 -> addCollectables(new Collectable(panel, semiAutoAmmo, col,row));
                case 5 -> addCollectables(new Collectable(panel, rifleAmmo, col,row));
                case 6 -> addCollectables(new Collectable(panel, tommyGunAmmo, col,row));
            }
            System.out.println("added collectable");
        }
        }
    }
}
