package collectables;

import items.*;
import logic.ApplicationPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Manages specific collectables.
 */
public class CollectableManager {
    ArrayList<Collectable> collectables = new ArrayList<>();
    ApplicationPanel panel;
    Bandage bandage;
    EnergyDrink energyDrink;
    RevolverAmmo revolverAmmo;
    PistolAmmo pistolAmmo;
    SemiAutoAmmo semiAutoAmmo;
    AssaultRifleAmmo rifleAmmo;
    TommyGunAmmo tommyGunAmmo;
    public CollectableManager(ApplicationPanel panel) {
        this.panel = panel;
        instantiate();
    }

    /**
     * Instantiates collectables.
     * It is necessary to store the same items in memory, so the program works properly and is more memory safe.
     */
    private void instantiate(){
        bandage = new Bandage(panel);
        energyDrink = new EnergyDrink(panel);
        revolverAmmo = new RevolverAmmo(panel);
        pistolAmmo = new PistolAmmo(panel);
        semiAutoAmmo = new SemiAutoAmmo(panel);
        rifleAmmo = new AssaultRifleAmmo(panel);
        tommyGunAmmo = new TommyGunAmmo(panel);
    }

    /**
     * Adds new collectable.
     *
     * @param collectable the collectable to be added
     */
    public void addCollectables(Collectable collectable){
        if(collectable != null){
            collectables.add(collectable);
        }
    }

    /**
     * Checks whether any collectable has been collected.
     * If so, the collectable will be removed from collectables ArrayList.
     */
    public void checkCollectables() {
        Iterator<Collectable> iterator = collectables.iterator();
        while (iterator.hasNext()) {
            Collectable collectable = iterator.next();
            if (collectable.intersectsPlayer() && panel.getPlayer().getInventory().getItems().size() < 10) {
                collectable.getItem().collect();
                panel.getEffectManager().addPickUpEffects();
                iterator.remove();
            }
        }
    }

    /**
     * Draws all collectables from collectables ArrayList to the screen.
     *
     * @param g the Graphics2D context on which to draw the collectables.
     */
    public void drawCollectables(Graphics2D g) {
        for (Collectable collectable : collectables) {
            if (collectable != null) {
                collectable.draw(g);
            }
        }
    }

    /**
     * Adds random collectables on a specific area.
     */
    public void addRandomCollectables(){
        Random rn = new Random();
        int col;
        int row;
        int chosen;
        for (int i = 0; i < 7; i++){
            col = rn.nextInt(15)+61;
            row = rn.nextInt(17)+61;
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
        }
        }
    }
}
