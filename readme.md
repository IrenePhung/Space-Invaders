# Requirements

- Gradle 7.4.2
- JDK 17

# How to run
First time running:
```
gradle clean build run
```
Already built:
```
gradle :run
```

# Design patterns used
## Factory method
The factory method is used to create a projectile.
The factory method's interface and implementation are the package: `invaders.entities.projectileFactories`.
The package contains the files and classes used for the factory method:
- `ProjectileFactory.java` - `class ProjectileFactory`: interface for the factory method
- `PlayerProjectileFactory.java` - `class PlayerProjectileFactory`: implementation of the factory method for creating player projectiles
- `EnemyProjectileFactory.java` - `class EnemyProjectileFactory`: implementation of the factory method for creating enemy projectiles

## State pattern
The state pattern is used to manage the states and colour changes of a bunker.
The state pattern's interface and implementation are the package: `invaders.entities.bunkerStates`.
The package contains the files and classes used for the state pattern:
- `BunkerState.java` - `class BunkerState`: interface for the state pattern
- `InitState.java` - `class InitState`: implementation of the state pattern for the initial state of a bunker
- `FirstHitState.java` - `class FirstHitState`: implementation of the state pattern for the first hit state of a bunker
- `SecHitState.java` - `class SecHitState`: implementation of the state pattern for the second hit state of a bunker
- `DestroyedState.java` - `class DestroyedState`: implementation of the state pattern for the destroyed state of a bunker

## Builder pattern
The enemies and bunkers must be created using the Builder pattern.
The builder pattern's interface and implementation are the package: `invaders.entities.builders`.
The package contains the files and classes used for the builder pattern:
- `Builder.java` - `class Builder`: interface for the builder pattern
- `AlienBuilder.java` - `class AlienBuilder`: implementation of the builder pattern for creating aliens
- `BunkerBuilder.java` - `class BunkerBuilder`: implementation of the builder pattern for creating bunkers

## Strategy pattern
The behaviour of projectiles will be controlled using the Strategy pattern
The strategy pattern's interface and implementation are the package: `invaders.entities.projectileStrategies`.
The package contains the files and classes used for the strategy pattern:
- `ProjectileStrategy.java` - `class ProjectileStrategy`: interface for the strategy pattern
- `SlowStraightStrategy.java` - `class SlowStraightStrategy`: implementation of the slow straight projectile strategy
- `FastStraightStrategy.java` - `class FastStraightStrategy`: implementation of the fast straight projectile strategy
