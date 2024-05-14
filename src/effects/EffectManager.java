package effects;

import world.ApplicationPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EffectManager {

    private ArrayList<Particle> particles = new ArrayList<>();
    private ApplicationPanel panel;

    public EffectManager(ApplicationPanel panel) {
        this.panel = panel;
    }


    public void update() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            if (particle != null) {
                if (particle.getDuration() > 0) {
                    particle.update();
                }else iterator.remove();
            }
        }
    }

    public void drawParticles(Graphics2D g){
        for (Particle particle: particles){
            if (particle != null){
                particle.draw(g);
            }
        }
    }


    public void addHitParticles(double x, double y) {
        Color color = new Color(255, 0, 30);
        particles.add(new Particle(panel, x, y, -1,-1,1,20, color));
        particles.add(new Particle(panel, x, y, -2,1,1,20, color));
        particles.add(new Particle(panel, x, y, 2,-1,1,20, color));
        particles.add(new Particle(panel, x, y, 1,2,1,20, color));
        particles.add(new Particle(panel, x, y, -2,-2,1,20, color));
        particles.add(new Particle(panel, x, y, -2,2,1,20, color));
        particles.add(new Particle(panel, x, y, 2,-2,1,20, color));

    }

    public void addWaterParticles(int x, int y) {

    }

    public void addGroundParticles(int x, int y) {

    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }
}