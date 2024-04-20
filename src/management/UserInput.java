package management;

import world.Panel;
import world.Status;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInput implements KeyListener {

    private int direction = 0;
    private boolean pressed;
    private boolean shiftPressed;

    Panel panel;

    public UserInput(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();
        switch (panel.getStatus()) {
            case SETUP -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        panel.setChosenOption(panel.getChosenOption() - 1);
                        if (panel.getChosenOption() < 0) {
                            panel.setChosenOption(1);
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        panel.setChosenOption(panel.getChosenOption() + 1);
                        if (panel.getChosenOption() > 1) {
                            panel.setChosenOption(0);
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if (panel.getChosenOption() == 0) {
                            panel.setStatus(Status.CUSTOMIZATION);
                        } else {
                            System.exit(0);
                        }
                    }
                }
            }
            case CUSTOMIZATION -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        panel.setChosenOption(panel.getChosenOption() - 1);
                        if (panel.getChosenOption() < 0) {
                            panel.setChosenOption(3);
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        panel.setChosenOption(panel.getChosenOption() + 1);
                        if (panel.getChosenOption() > 3) {
                            panel.setChosenOption(0);
                        }
                    }
                    case KeyEvent.VK_D, KeyEvent.VK_LEFT -> {
                        switch (panel.getChosenOption()) {
                            case 0 -> {
                                if (panel.getUi().getHealthCounter() + 1 < 11) {
                                    panel.getUi().setHealthCounter(panel.getUi().getHealthCounter() + 1);
                                }
                            }
                            case 1 -> {
                                if (panel.getUi().getStaminaCounter() + 1 < 11) {
                                    panel.getUi().setStaminaCounter(panel.getUi().getStaminaCounter() + 1);
                                }
                            }
                            case 2 -> {
                                if (panel.getUi().getSpeedCounter() + 1 < 11) {
                                    panel.getUi().setSpeedCounter(panel.getUi().getSpeedCounter() + 1);
                                }
                            }
                        }
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_RIGHT -> {
                        switch (panel.getChosenOption()) {
                            case 0 -> {
                                if (panel.getUi().getHealthCounter() - 1 >= 0) {
                                    panel.getUi().setHealthCounter(panel.getUi().getHealthCounter() - 1);
                                }
                            }
                            case 1 -> {
                                if (panel.getUi().getStaminaCounter() - 1 >= 0) {
                                    panel.getUi().setStaminaCounter(panel.getUi().getStaminaCounter() - 1);
                                }
                            }
                            case 2 -> {
                                if (panel.getUi().getSpeedCounter() - 1 >= 0) {
                                    panel.getUi().setSpeedCounter(panel.getUi().getSpeedCounter() - 1);
                                }
                            }
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if (panel.getChosenOption() == 3) {
                            panel.setStatus(Status.PLAYING);
                        }
                    }
                }
            }
            case PLAYING -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        direction = 0;
                        pressed = true;
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        direction = 1;
                        pressed = true;
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                        direction = 2;
                        pressed = true;
                    }
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                        direction = 3;
                        pressed = true;
                    }
                    case KeyEvent.VK_SHIFT -> {
                        shiftPressed = true;
                    }
                    default -> {
                        pressed = false;
                        shiftPressed = false;
                    }
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int input = e.getKeyCode();
        switch (input) {
            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_D, KeyEvent.VK_RIGHT, KeyEvent.VK_S, KeyEvent.VK_DOWN, KeyEvent.VK_A, KeyEvent.VK_LEFT ->
                    pressed = false;
            case KeyEvent.VK_SHIFT -> shiftPressed = false;
        }
    }

    public int getDirection() {
        return direction;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isShiftPressed() {
        return shiftPressed;
    }
}
