package invaders.entities.projectileStrategies;

import invaders.physics.Vector2D;

/**
 * A fast straight strategy for the projectile
 */
public class FastStraightStrategy implements ProjectileStrategy{

    @Override
    public void execute(Vector2D position, int speed) {
        position.setY(position.getY() + 2 * speed);
    }
    
}

