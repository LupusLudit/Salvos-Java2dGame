package management;

import logic.ApplicationPanel;
import logic.Weapon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The type User input.
 */
public class UserInput implements KeyListener {
    private boolean pressed;
    private boolean shiftPressed;

    private boolean reloadTriggered;

    /**
     * The Panel.
     */
    ApplicationPanel panel;

    /**
     * Instantiates a new User input.
     *
     * @param applicationPanel the application panel
     */
    public UserInput(ApplicationPanel applicationPanel) {
        this.panel = applicationPanel;
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
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                        if (panel.getChosenOption() != 3){
                            panel.getGame().addBonus(panel.getChosenOption());
                        }
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                        if (panel.getChosenOption() != 3) {
                            panel.getGame().subtractBonus(panel.getChosenOption());
                        }
                    }case KeyEvent.VK_ENTER -> {
                        if (panel.getChosenOption() == 3) {
                            panel.setStatus(Status.PLAYING);
                            panel.getEffectManager().addHintEffect();
                        }
                    }
                }
            }
            case PLAYING -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        panel.getPlayer().setDirection(0);
                        pressed = true;
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        panel.getPlayer().setDirection(1);
                        pressed = true;
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                        panel.getPlayer().setDirection(2);
                        pressed = true;
                    }
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                        panel.getPlayer().setDirection(3);
                        pressed = true;
                    }
                    case KeyEvent.VK_SHIFT -> shiftPressed = true;
                    case KeyEvent.VK_B -> panel.setStatus(Status.SHOP);
                    case KeyEvent.VK_R -> reloadTriggered = true;

                    case KeyEvent.VK_I -> panel.setStatus(Status.INVENTORY);
                    default -> {
                        pressed = false;
                        shiftPressed = false;
                        reloadTriggered = false;
                    }
                }
            }

            case INVENTORY -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> panel.getPlayer().getInventory().subtractRow();
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> panel.getPlayer().getInventory().addRow();
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> panel.getPlayer().getInventory().subtractCol();
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> panel.getPlayer().getInventory().addCol();
                    case KeyEvent.VK_I -> panel.setStatus(Status.PLAYING);
                    case KeyEvent.VK_E -> {
                        items.Item item = panel.getUi().getSelectedItem();
                        if (item != null && panel.getPlayer().getInventory().getItems().get(item) > 0) {
                            item.use();
                        } else {
                            panel.getGame().setSelectedWeapon(Weapon.FIST);
                        }
                    }
                }
            }

            case SHOP -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> panel.getShop().subtractRow();
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> panel.getShop().addRow();
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> panel.getShop().subtractCol();
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> panel.getShop().addCol();
                    case KeyEvent.VK_B -> panel.setStatus(Status.PLAYING);
                    case KeyEvent.VK_E -> {
                        if (panel.getGame().getScore() >= panel.getShop().getSelectedItem().getPrice() && panel.getPlayer().getInventory().getItems().size() < 10) {
                            int newScore = panel.getGame().getScore() - panel.getShop().getSelectedItem().getPrice();
                            panel.getGame().setScore(newScore);
                            panel.getShop().getSelectedItem().collect();
                        }
                    }
                }
            }
            case GAMEOVER -> {
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
                            panel.restart();

                        } else {
                            System.exit(0);
                        }
                    }
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int input = e.getKeyCode();
        switch (input) {
            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_D, KeyEvent.VK_RIGHT, KeyEvent.VK_S, KeyEvent.VK_DOWN, KeyEvent.VK_A, KeyEvent.VK_LEFT -> pressed = false;
            case KeyEvent.VK_SHIFT -> shiftPressed = false;
        }
    }

    /**
     * Sets reload triggered.
     *
     * @param reloadTriggered the reload triggered
     */
    public void setReloadTriggered(boolean reloadTriggered) {
        this.reloadTriggered = reloadTriggered;
    }

    /**
     * Is pressed boolean.
     *
     * @return the boolean
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Is shift pressed boolean.
     *
     * @return the boolean
     */
    public boolean isShiftPressed() {
        return shiftPressed;
    }

    /**
     * Is reload triggered boolean.
     *
     * @return the boolean
     */
    public boolean isReloadTriggered() {
        return reloadTriggered;
    }
}