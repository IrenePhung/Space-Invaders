package invaders.entities;

import invaders.GameObject;
import invaders.entities.bunkerStates.BunkerState;
import invaders.entities.bunkerStates.DestroyedState;
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
 * This class represents a bunker in the game
 */
public class Bunker implements GameObject, Damagable, Renderable, Collidable {
    private Vector2D position;
    private int width;
    private int height;

    private WritableImage image;
    BunkerState state;

    private final Collider collider;

    public Bunker() {
        position = new Vector2D(0, 0);
        width = 0;
        height = 0;

        collider = new BoxCollider(width, height, position);

        this.image = null;
    }

    @Override
    public Image getImage() {
        return image;
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
    public void takeDamage(double amount) {
        state.takeDamage(this);
    }

    @Override
    public double getHealth() {
        return state.getHealth();
    }

    @Override
    public boolean isAlive() {
        return state.getHealth() > 0;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    /**
     * Set the state of the bunker
     * @param newState the new state of the bunker
     */
    public void setState(BunkerState newState) {
        state = newState;
        ImageUtils.fillImgWithColour(image, state.getColour());
    }

    /**
     * Set the position of the bunker
     * @param position the new position of the bunker
     */
    public void setPosition(Vector2D position) {
        this.position = position;
        collider.setPosition(position);
    }

    /**
     * Set the size of the bunker
     * @param width the new width of the bunker
     * @param height the new height of the bunker
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new WritableImage(width, height);
        collider.setWidth(width);
        collider.setHeight(height);
    }

    @Override
    public Collider getCollider() {
        return collider;
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.BUNKER;
    }

    @Override
    public void onCollision(CollisionType collidedWith) {
        switch (collidedWith) {
            case PROJECTILE_ALIEN:
                takeDamage(1);
                break;
            case PROJECTILE_PLAYER:
                takeDamage(1);
                break;
            case ALIEN:
                setState(new DestroyedState());
            default:
                break;
        }
    }

    @Override
    public boolean isRendered() {
        return isAlive();
    }
}

