package invaders.entities;

import java.net.URL;
import java.util.Random;

import invaders.GameObject;
import invaders.entities.projectileFactories.ProjectileFactory;
import invaders.logic.Collidable;
import invaders.logic.Damagable;
import invaders.logic.Shootable;
import invaders.physics.BoxCollider;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.utilities.ImageUtils;
import javafx.scene.image.Image;

/**
 * The alien/enemy class
 */
public class Alien implements GameObject, Renderable, Damagable, Collidable, Shootable {
    private final double width = 44;
    private final double height = 28;
    private final double fireRate = 0.01;

    private final URL IMG_URL = getClass().getResource("/enemy.png");
    private final Image IMG = ImageUtils.loadImageWithColour(IMG_URL, width, height, "green");
    private final Random rng = new Random();

    private final Vector2D position;
    private final Collider collider;
    private String projectileStrat;
    private ProjectileFactory projectileFactory;
    private Projectile unreleasedProjectile;

    private double health;

    public Alien() {
        this.position = new Vector2D(0, 0);
        this.collider = new BoxCollider(width, height, position);

        health = 1;
    }

    /**
     * Sets the position of the alien
     * @param x The x position
     * @param y The y position
     */
    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
    }

    /**
     * Sets the projectile strategy
     * @param strat the strategy to set
     */
    public void setProjectileStrat(String strat) {
        projectileStrat = strat;
    }

    /**
     * Set the projectile factory
     * @param pf a reference to the projectile factory
     */
    public void setProjectileFactory(ProjectileFactory pf) {
        projectileFactory = pf;
    }

    @Override
    public Collider getCollider() {
        return collider;
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.ALIEN;
    }

    @Override
    public void onCollision(CollisionType collidedWith) {
        switch (collidedWith) {
            case PROJECTILE_PLAYER:
                takeDamage(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void takeDamage(double amount) {
        health = 0;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public Image getImage() {
        return IMG;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    public boolean isRendered() {
        return isAlive();
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        if (unreleasedProjectile == null) {
            if (rng.nextDouble() < fireRate) {
                shoot();
            }
        }
    }

    @Override
    public void shoot() {
        unreleasedProjectile = projectileFactory.create(position, (int) width, (int) height, projectileStrat);
    }

    @Override
    public Projectile releaseProjectiles() {
        Projectile releasing = unreleasedProjectile;
        unreleasedProjectile = null;
        return releasing;
    }

}
