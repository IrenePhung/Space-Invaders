package invaders.entities;

import invaders.GameObject;
import invaders.entities.projectileStrategies.ProjectileStrategy;
import invaders.logic.Collidable;
import invaders.logic.Damagable;
import invaders.physics.BoxCollider;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.utilities.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * This class represents a projectile in the game
 */
public class Projectile implements GameObject, Renderable, Damagable, Collidable {
    static final int WIDTH = 5;
    static final int HEIGHT = 10;
    static final int DEFAULT_SPEED = 2;
    static final Image IMAGE = ImageUtils.fillImgWithColour(new WritableImage(WIDTH, HEIGHT), "green");

    private Vector2D position;
    private Vector2D oldPosition;
    private int speed;
    private ProjectileStrategy strategy;
    private boolean isActive;

    private final Collider collider;
    private final Collidable.CollisionType collisionType;

    public Projectile(Vector2D position, boolean goingUp, ProjectileStrategy strategy) {
        this.position = position;
        this.strategy = strategy;
        this.isActive = true;

        oldPosition = new Vector2D(-1, -1);

        collider = new BoxCollider(WIDTH, HEIGHT, position);

        if (goingUp) {
            this.speed = -1 * DEFAULT_SPEED;
            collisionType = CollisionType.PROJECTILE_PLAYER;
        } else {
            this.speed = DEFAULT_SPEED;
            collisionType = CollisionType.PROJECTILE_ALIEN;
        }
    }

    @Override
    public void takeDamage(double amount) {
        isActive = false;
    }

    @Override
    public double getHealth() {
        if (isActive)
            return 1;
        return 0;
    }

    @Override
    public boolean isAlive() {
        return isActive;
    }

    @Override
    public Image getImage() {
        return IMAGE;
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
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
    public void start() {

    }

    @Override
    public void update() {
        if (position.getX() != oldPosition.getX() || position.getY() != oldPosition.getY()) {
            oldPosition.setX(position.getX());
            oldPosition.setY(position.getY());
            strategy.execute(position, speed);
        } else {
            isActive = false;
        }
    }

    /**
     * Sets the position of the projectile
     * @param position the new position
     */
    public void setPosition(Vector2D position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    /**
     * Sets the strategy of the projectile
     * @param strategy the new strategy
     */
    public void setStrategy(ProjectileStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Collider getCollider() {
        return collider;
    }

    @Override
    public CollisionType getCollisionType() {
        return collisionType;
    }

    @Override
    public void onCollision(CollisionType collidedWith) {
        if (collisionType == CollisionType.PROJECTILE_PLAYER) {
            switch (collidedWith) {
                case ALIEN:
                    isActive = false;
                    break;
                case BUNKER:
                    isActive = false;
                    break;
                case PROJECTILE_ALIEN:
                    isActive = false;
                    break;
                default:
                    break;
            }
        } else {
            switch (collidedWith) {
                case PLAYER:
                    isActive = false;
                    break;
                case BUNKER:
                    isActive = false;
                    break;
                case PROJECTILE_PLAYER:
                    isActive = false;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean isRendered() {
        return isActive;
    }
}
