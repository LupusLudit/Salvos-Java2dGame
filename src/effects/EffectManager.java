package effects;

import entities.Entity;
import logic.ApplicationPanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages specific effects.
 */
public class EffectManager {

    private final List<Effect> effects = new ArrayList<>();
    private final ApplicationPanel panel;
    public EffectManager(ApplicationPanel panel) {
        this.panel = panel;
    }

    /**
     * Updates all effects.
     * If the effects duration equals 0, the program will remove this effect, so it doesn't appear on the screen.
     */
    public void update() {
        Iterator<Effect> iterator = effects.iterator();
        while (iterator.hasNext()) {
            Effect effect = iterator.next();
            if (effect != null) {
                if (effect.getDuration() > 0) {
                    effect.update();
                } else {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Draws all effects from effects ArrayList.
     *
     * @param g the Graphics2D context on which to draw the effects.
     * @throws IOException if the program couldn't find the image on the specific address.
     */
    public void drawEffects(Graphics2D g) throws IOException {
        List<Effect> effectsCopy = new ArrayList<>(effects);
        for (Effect effect : effectsCopy) {
            if (effect != null) {
                effect.draw(g);
            }
        }
    }

    /**
     * Adds hit particles when an entity is hit.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void addHitParticles(double x, double y) {
        Color color = new Color(255, 0, 30);
        effects.add(new Particle(panel, x, y, -1,-1,1,15, color));
        effects.add(new Particle(panel, x, y, -2,1,1,15, color));
        effects.add(new Particle(panel, x, y, 2,-1,1,15, color));
        effects.add(new Particle(panel, x, y, 1,2,1,15, color));
        effects.add(new Particle(panel, x, y, -2,-2,1,15, color));
        effects.add(new Particle(panel, x, y, -2,2,1,15, color));
        effects.add(new Particle(panel, x, y, 2,-2,1,15, color));

    }

    /**
     * Adds radioactive particles when radioactive tile is hit.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void addRadioactiveParticles(int x, int y) {
        Color color = new Color(44, 249, 29);
        effects.add(new Particle(panel, x, y, -1,-2,0.7,15, color));
        effects.add(new Particle(panel, x, y, -2,-3,0.7,15, color));
        effects.add(new Particle(panel, x, y, 0.5,-2,0.7,15, color));
        effects.add(new Particle(panel, x, y, 1,-1.5,0.7,15, color));
        effects.add(new Particle(panel, x, y, -1.5,2,0.7,15, color));
        effects.add(new Particle(panel, x, y, 1.5,1,0.7,15, color));
        effects.add(new Particle(panel, x, y, 3,1,0.7,15, color));
    }

    /**
     * Adds ground particles when ground tile is hit.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void addGroundParticles(int x, int y) {
        Color color = new Color(116, 55, 39);
        effects.add(new Particle(panel, x, y, -2,-2,0.7,15, color));
        effects.add(new Particle(panel, x, y, -1,-3,0.7,15, color));
        effects.add(new Particle(panel, x, y, 0.5,-2,0.7,15, color));
        effects.add(new Particle(panel, x, y, 2.5,-3,0.7,15, color));
        effects.add(new Particle(panel, x, y, -2,2,0.7,15, color));
        effects.add(new Particle(panel, x, y, 1,1,0.7,15, color));
        effects.add(new Particle(panel, x, y, 2,2,0.7,15, color));
    }

    /**
     * Adds rock particles when rocky tile is hit.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void addRockParticles(int x, int y) {
        Color color = new Color(70, 70, 70);
        effects.add(new Particle(panel, x, y, 1.5,-2,0.7,20, color));
        effects.add(new Particle(panel, x, y, -2,-2.5,0.7,20, color));
        effects.add(new Particle(panel, x, y, 0,-1,0.7,20, color));
        effects.add(new Particle(panel, x, y, 3,-2,0.7,20, color));
        effects.add(new Particle(panel, x, y, -2,2.5,0.7,20, color));
        effects.add(new Particle(panel, x, y, 1.5,1,0.7,20, color));
        effects.add(new Particle(panel, x, y, 2,1,0.7,20, color));

    }

    /**
     * Adds pick up effects.
     */
    public void addPickUpEffects(){
        effects.add(new PickUp(panel,120));
    }

    /**
     * Adds biting effect.
     *
     * @param entity the entity above which should the biting effect be displayed.
     */
    public void addBitingEffect(Entity entity){
        effects.add(new BitingEffect(panel, 30, entity));
    }

    /**
     * Adds flashing effect.
     *
     * @param entity the entity on which should the flashing effect be displayed.
     */
    public void addFlashingEffect(Entity entity){
        effects.add(new FlashingEffect(panel, entity, 20));
    }

    /**
     * Adds blasting effect.
     *
     * @param direction the direction at which should the blasting effect be displayed.
     */
    public void addBlastingEffect(int direction){
        effects.add(new Blasting(panel, 7, direction));
    }

    /**
     * Adds punching effect.
     *
     * @param direction the direction at which should the punching effect be displayed.
     */
    public void addPunchingEffect(int direction){
        effects.add(new PunchingEffect(panel, 30, direction));
    }

    /**
     * Adds reloading effects.
     */
    public void addReloadingEffect(){
        effects.add(new ReloadingEffect(panel,100));
    }

    /**
     * Adds hint effects.
     */
    public void addHintEffect(){
        effects.add(new Hint(panel, 1200));
    }
}

