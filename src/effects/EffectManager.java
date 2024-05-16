package effects;

import entities.Entity;
import world.ApplicationPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EffectManager {

    private ArrayList<Effect> effects = new ArrayList<>();
    private ApplicationPanel panel;

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
                }else iterator.remove();
            }
        }
    }

    public void drawParticles(Graphics2D g) {
        Iterator<Effect> iterator = effects.iterator();
        while (iterator.hasNext()) {
            Effect effect = iterator.next();
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

    public void addWaterParticles(int x, int y) {
    }

    public void addGroundParticles(int x, int y) {
        Color color = new Color(80, 40, 20);
        effects.add(new Particle(panel, x, y, -2,-2,0.7,15, color));
        effects.add(new Particle(panel, x, y, -2,-3,0.7,15, color));
        effects.add(new Particle(panel, x, y, 0,-2,0.7,15, color));
        effects.add(new Particle(panel, x, y, 3,-3,0.7,15, color));
        effects.add(new Particle(panel, x, y, -2,2,0.7,15, color));
        effects.add(new Particle(panel, x, y, 1,1,0.7,15, color));
        effects.add(new Particle(panel, x, y, 2,2,0.7,15, color));
    }

    public void addFlashingEffect(Entity entity){
        effects.add(new FlashingEffect(panel, entity, 20));
    }

    public void addBlastingEffect(int direction){
        effects.add(new Blasting(panel, 10, direction));
    }
}
