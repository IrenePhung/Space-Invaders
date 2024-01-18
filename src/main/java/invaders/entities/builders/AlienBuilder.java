package invaders.entities.builders;

import invaders.GameObject;
import invaders.entities.Alien;
import invaders.entities.projectileFactories.ProjectileFactory;

/**
 * The builder for the alien class
 */
public class AlienBuilder implements Builder{
    private Alien alien;

    public AlienBuilder(){
        alien = new Alien();
    }

    @Override
    public void reset() {
        alien = new Alien();
    }

    @Override
    public void addPos(int x, int y) {
        alien.setPosition(x, y);
    }

    @Override
    public void addSize(int x, int y) {

    }

    @Override
    public GameObject getResultGameObj() {
        return alien;
    }

    @Override
    public void addProjectileFact(ProjectileFactory pf) {
        alien.setProjectileFactory(pf);
    }
    
}

