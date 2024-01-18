package invaders.entities.projectileFactories;

import invaders.entities.Projectile;
import invaders.physics.Vector2D;

/**
 * The interface different projectile factories must implement,
 * implementing the factory pattern
 */
public interface ProjectileFactory {
    /**
     * Creates a projectile, the default strategy is used (slow_straight strategy)
     * @param position The position of the shooter
     * @param width The width of the shooter
     * @param height The height of the shooter
     * @return the projectile created, or null if the projectile cannot be created
     */
    public Projectile create(Vector2D position, int width, int height);

    /**
     * Creates a projectile with a specific strategy
     * @param position The position of the shooter
     * @param width The width of the shooter
     * @param height The height of the shooter
     * @param strategy The strategy of the projectile
     * @return the projectile created, or null if the projectile cannot be created
     */
    public Projectile create(Vector2D position, int width, int height, String strategy);
}

