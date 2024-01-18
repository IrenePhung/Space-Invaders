package invaders.entities.projectileStrategies;

import invaders.physics.Vector2D;

/**
 * The interface different projectile strategies must implement,
 * implementing the strategy pattern
 */
public interface ProjectileStrategy {
    /**
     * Executes the strategy
     * @param position a reference to the position of the projectile
     * @param speed the speed of the projectile
     */
    public void execute(Vector2D position, int speed);
}

