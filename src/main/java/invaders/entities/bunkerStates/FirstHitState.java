package invaders.entities.bunkerStates;

import invaders.entities.Bunker;

/**
 * Represents the state of the bunker when it has been hit once
 */
public class FirstHitState implements BunkerState {

    @Override
    public void takeDamage(Bunker bunker) {
        bunker.setState(new SecHitState());
    }

    @Override
    public double getHealth() {
        return 2;
    }

    @Override
    public String getColour() {
        return "yellow";
    }
    
}

