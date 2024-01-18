package invaders.entities.projectileFactories;

import java.util.ArrayList;
import java.util.List;

import invaders.entities.Projectile;
import invaders.entities.projectileStrategies.ProjectileStrategy;
import invaders.entities.projectileStrategies.SlowStraightStrategy;
import invaders.physics.Vector2D;

/**
 * The factory for creating player projectiles
 */
public class PlayerProjectileFactory implements ProjectileFactory {
    private final List<Projectile> projectilePool;
    private final ProjectileStrategy playerStrategy;
    private final int maxProjectile;

    /**
     * Constructor for the player projectile factory
     * @param maxProjectile the maximum number of projectiles that can be created at once
     */
    public PlayerProjectileFactory(int maxProjectile) {
        playerStrategy = new SlowStraightStrategy();
        projectilePool = new ArrayList<>(maxProjectile);
        this.maxProjectile = maxProjectile;
    }

    /**
     * Cleans the pool of projectiles
     */
    private void cleanPool() {
        projectilePool.removeIf(projectile -> !projectile.isAlive());
    }

    @Override
    public Projectile create(Vector2D position, int width, int height) {
        Vector2D adjustedPosition = new Vector2D(position.getX() + width / 2, position.getY());
        cleanPool();
        if (projectilePool.size() < maxProjectile) {
            Projectile projectile = new Projectile(adjustedPosition, true, playerStrategy);
            projectilePool.add(projectile);
            return projectile;
        }
        return null;
    }

    @Override
    public Projectile create(Vector2D position, int width, int height, String strategy) {
        return create(position, width, height);
    }

}

