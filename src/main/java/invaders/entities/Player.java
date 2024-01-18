package invaders.entities;

import java.net.URL;

import invaders.entities.projectileFactories.ProjectileFactory;
import invaders.logic.Collidable;
import invaders.logic.Damagable;
import invaders.logic.Shootable;
import invaders.physics.BoxCollider;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.utilities.ImageUtils;

import javafx.scene.image.Image;

public class Player implements Moveable, Damagable, Renderable, Collidable, Shootable {

    private final Vector2D position;
    // private final Animator anim = null;
    private double health = 100;
    private final int speed;

    private final double width = 25;
    private final double height = 30;
    private final Image image;

    private final ProjectileFactory projectileFactory;
    private Projectile unreleasedProjectile;

    private final Collider collider;

    public Player(Vector2D position, String colour, int speed, int lives, ProjectileFactory projectileFactory) {

        this.position = position;
        this.speed = speed;
        this.health = lives;

        this.projectileFactory = projectileFactory;

        collider = new BoxCollider(width, height, position);
        unreleasedProjectile = null;

        URL img_url = getClass().getResource("/player.png");
        image = ImageUtils.loadImageWithColour(img_url, width, height, colour);
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - speed);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + speed);
    }

    public void shoot() {
        unreleasedProjectile = projectileFactory.create(position, (int) width, (int) height);
    }

    @Override
    public Image getImage() {
        return this.image;
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
    public Collider getCollider() {
        return collider;
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.PLAYER;
    }

    @Override
    public void onCollision(CollisionType collidedWith) {
        switch (collidedWith) {
            case ALIEN:
                takeDamage(health);
                break;
            case PROJECTILE_ALIEN:
                takeDamage(1);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isRendered() {
        return isAlive();
    }

    @Override
    public Projectile releaseProjectiles() {
        Projectile releasing = unreleasedProjectile;
        unreleasedProjectile = null;
        return releasing;
    }

}

