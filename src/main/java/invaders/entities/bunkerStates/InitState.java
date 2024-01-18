package invaders.entities.bunkerStates;

import invaders.entities.Bunker;

/**
 * Represents the bunker initial state
 */
public class InitState implements BunkerState{

    @Override
    public void takeDamage(Bunker bunker) {
        bunker.setState(new FirstHitState());
    }

    @Override
    public double getHealth() {
        return 3;
    }

    @Override
    public String getColour() {
        return "green";
    }
}

