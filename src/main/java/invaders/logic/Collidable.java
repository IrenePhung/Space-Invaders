package invaders.logic;

import invaders.physics.Collider;

/**
 * The interface all collidable objects must implement
 */
public interface Collidable {
    /**
     * Returns the collider of the object
     * @return the collider of the object
     */
    public Collider getCollider();

    /**
     * The collision types
     */
    public enum CollisionType {
        PLAYER, ALIEN, BUNKER, PROJECTILE_PLAYER, PROJECTILE_ALIEN, NONE
    }

    /**
     * Returns the collision type of the object
     * @return the collision type of the object
     */
    public CollisionType getCollisionType();

    /**
     * Called when the object collides with another object
     * @param collidedWith the type of object it collided with
     */
    public void onCollision(CollisionType collidedWith);
}

