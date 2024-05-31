package management;

import logic.ApplicationPanel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Clock.
 */
public class Clock {
    private int counter;
    private boolean running;

    /**
     * The Timer.
     */
    Timer timer = new Timer();

    /**
     * Start.
     *
     * @param durationInSeconds the duration in seconds
     * @param applicationPanel  the application panel
     * @param mode              the mode
     */
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

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public boolean isRunning() {
        return running;
    }

}
