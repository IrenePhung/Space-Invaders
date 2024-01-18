package invaders.entities.bunkerStates;

import invaders.entities.Bunker;

/**
 * Represents the state of the bunker when it has been hit twice
 */
public class SecHitState implements BunkerState{

    @Override
    public void takeDamage(Bunker bunker) {
        bunker.setState(new DestroyedState());
    }

    @Override
    public double getHealth() {
        return 1;
    }

    @Override
    public String getColour() {
        return "red";
    }
}

