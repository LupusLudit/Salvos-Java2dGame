package management;

import logic.ApplicationPanel;

import java.util.Timer;
import java.util.TimerTask;
public class Clock {
    private int counter;
    private boolean running;
    Timer timer = new Timer();

    /**
     * Starts the clock.
     * Timer ensures that the clock will update every 1000 milliseconds (= 1 second)
     *
     * @param durationInSeconds the duration of selected effect in seconds
     * @param applicationPanel  the application panel
     * @param mode              the mode of the clock
     */
    public void start(int durationInSeconds, ApplicationPanel applicationPanel, ClockMode mode) {
        counter = durationInSeconds;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                switch (mode){
                    case SPEED_COUNTER -> applicationPanel.getPlayer().setTime(counter);
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
