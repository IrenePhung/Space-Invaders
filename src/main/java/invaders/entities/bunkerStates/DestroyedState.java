package invaders.entities.bunkerStates;

import invaders.entities.Bunker;

/**
 * Represents the bunker destroyed state
 */
public class DestroyedState implements BunkerState {

    @Override
    public void takeDamage(Bunker bunker) {

    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public String getColour() {
        return "black";
    }

}

