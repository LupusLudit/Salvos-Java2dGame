package management;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInput implements KeyListener {

    private int direction = 0;
    private boolean pressed;
    private boolean shiftPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();
        switch (input){
            case KeyEvent.VK_W, KeyEvent.VK_UP->{
                direction = 0;
                pressed = true;
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN ->{
                direction = 1;
                pressed = true;
            }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT ->{
                direction = 2;
                pressed = true;
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT ->{
                direction = 3;
                pressed = true;
            }
            case KeyEvent.VK_SHIFT -> {
                shiftPressed = true;
            }
            default ->{
                pressed = false;
                shiftPressed = false;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int input = e.getKeyCode();
        switch (input){
            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_D, KeyEvent.VK_RIGHT, KeyEvent.VK_S, KeyEvent.VK_DOWN, KeyEvent.VK_A, KeyEvent.VK_LEFT -> pressed = false;
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
