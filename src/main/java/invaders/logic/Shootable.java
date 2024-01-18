package invaders.logic;

import invaders.entities.Projectile;

/**
 * Represents an entity that can shoot projectiles
 */
public interface Shootable {
    /**
     * Represents the entity trying to shoot a projectile
     */
    public void shoot();

    /**
     * Represents the entity releasing the projectile
     * @return the released projectile
     */
    public Projectile releaseProjectiles();
}

