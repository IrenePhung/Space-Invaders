package invaders.entities.builders;

import invaders.GameObject;
import invaders.entities.projectileFactories.ProjectileFactory;

/**
 * The interface for the builder pattern
 * User for creating game objects
 */
public interface Builder {
    /**
     * Resets the builder
     */
    public void reset();

    /**
     * Adds the position of the game object
     * @param x the x position
     * @param y the y position
     */
    public void addPos(int x, int y);

    /**
     * Adds the size of the game object
     * @param x the x size
     * @param y the y size
     */
    public void addSize(int x, int y);

    /**
     * Adds the projectile factory to the game object
     * @param pf the projectile factory
     */
    public void addProjectileFact(ProjectileFactory pf);

    /**
     * Get the result of the builder
     * @return the game object as a result
     */
    public GameObject getResultGameObj();
}

