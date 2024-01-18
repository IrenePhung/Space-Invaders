package invaders.entities.builders;

import invaders.GameObject;
import invaders.entities.Bunker;
import invaders.entities.bunkerStates.InitState;
import invaders.entities.projectileFactories.ProjectileFactory;
import invaders.physics.Vector2D;

/**
 * The builder for the bunker class
 */
public class BunkerBuiler implements Builder{
    private Bunker bunker;

    public BunkerBuiler() {
        bunker = new Bunker();
    }

    @Override
    public void addPos(int x, int y) {
        Vector2D position = new Vector2D(x, y);
        bunker.setPosition(position);
    }

    @Override
    public void addSize(int x, int y) {
        bunker.setSize(x, y);
        bunker.setState(new InitState());
    }

    @Override
    public void reset() {
        bunker = new Bunker();
    }

    @Override
    public GameObject getResultGameObj() {
        return bunker;
    }

    @Override
    public void addProjectileFact(ProjectileFactory pf) {
        
    }
}

