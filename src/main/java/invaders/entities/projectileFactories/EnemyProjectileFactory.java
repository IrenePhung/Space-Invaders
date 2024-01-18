package invaders.entities.projectileFactories;

import java.util.ArrayList;
import java.util.List;

import invaders.entities.Projectile;
import invaders.entities.projectileStrategies.FastStraightStrategy;
import invaders.entities.projectileStrategies.ProjectileStrategy;
import invaders.entities.projectileStrategies.SlowStraightStrategy;
import invaders.physics.Vector2D;

/**
 * The factory for creating enemy projectiles
 */
public class EnemyProjectileFactory implements ProjectileFactory{
    private final int maxProjectile;
    private List<Projectile> projectilePool;

    /**
     * Constructor for the enemy projectile factory
     * @param maxProjectile the maximum number of projectiles that can be created at once
     */
    public EnemyProjectileFactory(int maxProjectile){
        projectilePool = new ArrayList<Projectile>(maxProjectile);
        this.maxProjectile = maxProjectile;
    }

    /**
     * Cleans the pool of projectiles
     */
    private void cleanPool() {
        projectilePool.removeIf(projectile -> !projectile.isAlive());
    }

    /**
     * Calculates the position of the projectile based on the shooter
     * @param position the position of the shooter
     * @param width the width of the shooter
     * @param height the height of the shooter
     * @return the position of the projectile
     */
    private Vector2D calculateProjPos(Vector2D position, int width, int height){
        Vector2D newPosition = new Vector2D(0, 0);
        newPosition.setX(position.getX() + width / 2);
        newPosition.setY(position.getY() + height);

        return newPosition;
    }

    @Override
    public Projectile create(Vector2D position, int width, int height) {
        cleanPool();
        if (projectilePool.size() < maxProjectile) {
            Vector2D newPosition = calculateProjPos(position, width, height);
            Projectile projectile = new Projectile(newPosition, false, new SlowStraightStrategy());
            projectilePool.add(projectile);
            return projectile;
        }
        return null;
    }

    @Override
    public Projectile create(Vector2D position, int width, int height, String strategy) {
        cleanPool();
        if (projectilePool.size() >= maxProjectile){
            return null;
        }

        ProjectileStrategy strat;
        if (strategy.equals("fast_straight")){
            strat = new FastStraightStrategy();
        } else {
            strat = new SlowStraightStrategy();
        }
        Vector2D newPosition = calculateProjPos(position, width, height);
        Projectile projectile = new Projectile(newPosition, false, strat);
        projectilePool.add(projectile);

        return projectile;
    }
    
}

