package management;

import world.ApplicationPanel;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private int counter;
    private boolean running;

    private Timer timer = new Timer();

    public void start(int durationInSeconds, ApplicationPanel applicationPanel, Mode mode) {
        counter = durationInSeconds;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                switch (mode){
                    case STAMINA_COUNTER -> applicationPanel.getPlayer().setTime(counter);
                    case WAVE_COUNTER -> applicationPanel.setWaveTimer(counter);
                }
                counter--;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
        TimerTask stopTask = new TimerTask() {
            @Override
            public void run() {
                running = false;
                timer.cancel();
            }
        };
        timer.schedule(stopTask, durationInSeconds * 1000 );
    }

    public boolean isRunning() {
        return running;
    }

}
