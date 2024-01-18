package invaders.entities.bunkerStates;

import invaders.entities.Bunker;
/*
 * Interface for the different states of a bunker,
 * implementing the state pattern
 */
public interface BunkerState{
    /**
     * Represents the bunker taking damage
     * @param bunker a reference to the bunker as a context
     */
    public abstract void takeDamage(Bunker bunker);

    /**
     * Represents the bunker current health
     * @return the health of the bunker at the current state
     */
    public abstract double getHealth();

    /**
     * Represents the bunker current colour
     * @return the colour name of the bunker at the current state
     */
    public abstract String getColour();
}

