package effects;

import entities.Entity;
import logic.ApplicationPanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffectManager {

    private final List<Effect> effects = new ArrayList<>();
    private final ApplicationPanel panel;

    public EffectManager(ApplicationPanel panel) {
        this.panel = panel;
    }

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

    public void drawParticles(Graphics2D g) throws IOException {
        List<Effect> effectsCopy = new ArrayList<>(effects);
        for (Effect effect : effectsCopy) {
            if (effect != null) {
                effect.draw(g);
            }
        }
    }

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

    public void addPickUpEffects(){
        effects.add(new PickUp(panel,120));
    }

    public void addBitingEffect(Entity entity){
        effects.add(new BitingEffect(panel, 30, entity));
    }

    public void addFlashingEffect(Entity entity){
        effects.add(new FlashingEffect(panel, entity, 20));
    }

    public void addBlastingEffect(int direction){
        effects.add(new Blasting(panel, 7, direction));
    }

    public void addPunchingEffect(int direction){
        effects.add(new PunchingEffect(panel, 30, direction));
    }

    public void addReloadingEffect(){
        effects.add(new ReloadingEffect(panel,100));
    }

    public void addHintEffect(){
        effects.add(new Hint(panel, 1200));
    }
}

