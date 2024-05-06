package management;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private int counter;
    private Timer timer = new Timer();

    world.Panel panel;

    public Clock(world.Panel panel) {
        this.panel = panel;
    }

    public void start(int durationInSeconds) {
        counter = durationInSeconds;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                panel.getPlayer().setTime(counter);
                counter--;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
        TimerTask stopTask = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                panel.getPlayer().setASRunning(false);
            }
        };
        timer.schedule(stopTask, durationInSeconds * 1000);
    }

    public int getCounter() {
        return counter;
    }
}
