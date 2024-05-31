package items;

import logic.ApplicationPanel;

/**
 * The type Bandage.
 */
public class Bandage extends Item{

    /**
     * Instantiates a new Bandage.
     *
     * @param applicationPanel the application panel
     */
    public Bandage(ApplicationPanel applicationPanel) {
        super(applicationPanel);
        setImage("/items/bandage.png");
    }

    @Override
    public void use() {
        for (int i = 0; i < 5; i++){
            applicationPanel.getPlayer().increaseLives();
        }
        applicationPanel.getPlayer().getInventory().removeItem(this);
    }

    @Override
    public void collect() {
        applicationPanel.getPlayer().getInventory().addToInventory(this);
    }
}
