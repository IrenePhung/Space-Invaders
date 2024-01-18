package invaders.entities;

import java.util.ArrayList;
import java.util.List;

import invaders.GameObject;
import invaders.physics.Vector2D;

/**
 * A hive mind is the entity that controls the aliens
 */
public class HiveMind implements GameObject {
    private final double ySpeed = 5;
    private final double xVelocity = 0.05;
    private final double xLimit;

    private double xSpeed = 0.8;
    private boolean descending;

    private final List<Alien> aliens;

    public HiveMind(double xLimit) {
        this.xLimit = xLimit;
        aliens = new ArrayList<>();
        descending = false;
    }

    /**
     * Adds an alien to the hive mind
     * @param alien the alien to add
     */
    public void addAlien(Alien alien) {
        aliens.add(alien);
    }

    /**
     * Increases the speed of the aliens
     */
    public void increaseSpeed() {
        xSpeed += xVelocity * (xSpeed / Math.abs(xSpeed));
    }

    @Override
    public void start() {

    }

    /**
     * Moves the aliens downwards
     */
    private void descend() {
        for (Alien alien : aliens) {
            Vector2D position = alien.getPosition();
            position.setY(position.getY() + ySpeed);
        }
    }

    @Override
    public void update() {
        List<Alien> destroyedAliens = new ArrayList<>();
        for (Alien alien : aliens) {
            if (!alien.isAlive())
                destroyedAliens.add(alien);
        }

        for (Alien destroyed : destroyedAliens) {
            aliens.remove(destroyed);
            increaseSpeed();
        }

        if (descending) {
            descend();
            descending = false;
            return;
        }

        // Move the aliens
        for (Alien alien : aliens) {
            Vector2D position = alien.getPosition();
            position.setX(position.getX() + xSpeed);
        }

        // Check if any alien is out of bound
        for (Alien alien : aliens) {
            double newX = alien.getPosition().getX() + xSpeed;
            if ( newX <= 0 || newX + alien.getWidth() >= xLimit) {
                xSpeed = -xSpeed;
                descending = true;
                break;
            }
        }

    }

}

