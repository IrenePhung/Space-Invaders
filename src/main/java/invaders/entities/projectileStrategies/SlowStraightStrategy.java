package invaders.entities.projectileStrategies;

import invaders.physics.Vector2D;

/**
 * A slow straight strategy for the projectile
 */
public class SlowStraightStrategy implements ProjectileStrategy{

    @Override
    public void execute(Vector2D position, int speed) {
        position.setY(position.getY() + speed);
    }
    
}

