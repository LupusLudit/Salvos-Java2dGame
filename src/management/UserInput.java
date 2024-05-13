package management;

import world.Panel;
import world.Status;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInput implements KeyListener{

    private int direction = 0;
    private boolean pressed;
    private boolean shiftPressed;

    private boolean reloadTriggered;

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
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> panel.getGame().addBonus(panel.getChosenOption());
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> panel.getGame().subtractBonus(panel.getChosenOption());

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
                        if(panel.getGame().getScore() >= panel.getShop().getSelectedItem().getPrice() && panel.getPlayer().getInventory().getItems().size() < 10){
                            int newScore = panel.getGame().getScore() - panel.getShop().getSelectedItem().getPrice();
                            panel.getGame().setScore(newScore);
                            panel.getShop().getSelectedItem().collect();
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