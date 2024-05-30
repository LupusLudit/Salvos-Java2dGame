package management;

import world.ApplicationPanel;
import world.Status;
import world.Weapon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInput implements KeyListener {

    private int direction = 0;
    private boolean pressed;
    private boolean shiftPressed;

    private boolean reloadTriggered;

    ApplicationPanel applicationPanel;

    public UserInput(ApplicationPanel applicationPanel) {
        this.applicationPanel = applicationPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();
        switch (applicationPanel.getStatus()) {
            case SETUP -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        applicationPanel.setChosenOption(applicationPanel.getChosenOption() - 1);
                        if (applicationPanel.getChosenOption() < 0) {
                            applicationPanel.setChosenOption(1);
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        applicationPanel.setChosenOption(applicationPanel.getChosenOption() + 1);
                        if (applicationPanel.getChosenOption() > 1) {
                            applicationPanel.setChosenOption(0);
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if (applicationPanel.getChosenOption() == 0) {
                            applicationPanel.setStatus(Status.CUSTOMIZATION);
                        } else {
                            System.exit(0);
                        }
                    }
                }
            }
            case CUSTOMIZATION -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        applicationPanel.setChosenOption(applicationPanel.getChosenOption() - 1);
                        if (applicationPanel.getChosenOption() < 0) {
                            applicationPanel.setChosenOption(3);
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        applicationPanel.setChosenOption(applicationPanel.getChosenOption() + 1);
                        if (applicationPanel.getChosenOption() > 3) {
                            applicationPanel.setChosenOption(0);
                        }
                    }
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                        if (applicationPanel.getChosenOption() != 3){
                            applicationPanel.getGame().addBonus(applicationPanel.getChosenOption());
                        }
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                        if (applicationPanel.getChosenOption() != 3) {
                            applicationPanel.getGame().subtractBonus(applicationPanel.getChosenOption());
                        }
                    }case KeyEvent.VK_ENTER -> {
                        if (applicationPanel.getChosenOption() == 3) {
                            applicationPanel.setStatus(Status.PLAYING);
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
                    case KeyEvent.VK_SHIFT -> shiftPressed = true;
                    case KeyEvent.VK_B -> applicationPanel.setStatus(Status.SHOP);
                    case KeyEvent.VK_R -> reloadTriggered = true;

                    case KeyEvent.VK_I -> applicationPanel.setStatus(Status.INVENTORY);
                    default -> {
                        pressed = false;
                        shiftPressed = false;
                        reloadTriggered = false;
                    }
                }
            }

            case INVENTORY -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> applicationPanel.getPlayer().getInventory().subtractRow();
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> applicationPanel.getPlayer().getInventory().addRow();
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> applicationPanel.getPlayer().getInventory().subtractCol();
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> applicationPanel.getPlayer().getInventory().addCol();
                    case KeyEvent.VK_I -> applicationPanel.setStatus(Status.PLAYING);
                    case KeyEvent.VK_E -> {
                        items.Item item = applicationPanel.getUi().getSelectedItem();
                        if (item != null && applicationPanel.getPlayer().getInventory().getItems().get(item) > 0) {
                            item.use();
                        } else {
                            applicationPanel.getGame().setSelectedWeapon(Weapon.FIST);
                        }
                    }
                }
            }

            case SHOP -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> applicationPanel.getShop().subtractRow();
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> applicationPanel.getShop().addRow();
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> applicationPanel.getShop().subtractCol();
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> applicationPanel.getShop().addCol();
                    case KeyEvent.VK_B -> applicationPanel.setStatus(Status.PLAYING);
                    case KeyEvent.VK_E -> {
                        if (applicationPanel.getGame().getScore() >= applicationPanel.getShop().getSelectedItem().getPrice() && applicationPanel.getPlayer().getInventory().getItems().size() < 10) {
                            int newScore = applicationPanel.getGame().getScore() - applicationPanel.getShop().getSelectedItem().getPrice();
                            applicationPanel.getGame().setScore(newScore);
                            applicationPanel.getShop().getSelectedItem().collect();
                        }
                    }
                }
            }
            case GAMEOVER -> {
                switch (input) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        applicationPanel.setChosenOption(applicationPanel.getChosenOption() - 1);
                        if (applicationPanel.getChosenOption() < 0) {
                            applicationPanel.setChosenOption(1);
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        applicationPanel.setChosenOption(applicationPanel.getChosenOption() + 1);
                        if (applicationPanel.getChosenOption() > 1) {
                            applicationPanel.setChosenOption(0);
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if (applicationPanel.getChosenOption() == 0) {
                            applicationPanel.restart();

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
            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_D, KeyEvent.VK_RIGHT, KeyEvent.VK_S, KeyEvent.VK_DOWN, KeyEvent.VK_A, KeyEvent.VK_LEFT ->
                    pressed = false;
            case KeyEvent.VK_SHIFT -> shiftPressed = false;
        }
    }

    public void setReloadTriggered(boolean reloadTriggered) {
        this.reloadTriggered = reloadTriggered;
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

    public boolean isReloadTriggered() {
        return reloadTriggered;
    }
}