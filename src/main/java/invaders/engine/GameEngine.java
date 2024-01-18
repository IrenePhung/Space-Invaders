package invaders.engine;

import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import invaders.GameObject;
import invaders.entities.Alien;
import invaders.entities.Bunker;
import invaders.entities.HiveMind;
import invaders.entities.Player;
import invaders.entities.Projectile;
import invaders.entities.builders.AlienBuilder;
import invaders.entities.builders.Builder;
import invaders.entities.builders.BunkerBuiler;
import invaders.entities.projectileFactories.EnemyProjectileFactory;
import invaders.entities.projectileFactories.PlayerProjectileFactory;
import invaders.entities.projectileFactories.ProjectileFactory;
import invaders.logic.Collidable;
import invaders.logic.Shootable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private List<Collidable> collidables;
	private List<Shootable> shootables;
	private Player player;

	private boolean left;
	private boolean right;

	private Integer gameX;
	private Integer gameY;

	public GameEngine(String config) {
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
		collidables = new ArrayList<Collidable>();
		shootables = new ArrayList<Shootable>();

		parseConfig(config);
	}

	/**
	 * Parses the config file and creates the game objects
	 * @param config the path to the config file
	 */
	private void parseConfig(String config) {
		try {
			JSONParser parser = new JSONParser();
			URL confURL = getClass().getResource(config);
			Path confPath = Path.of(confURL.toURI());

			Object configObject = parser.parse(new FileReader(confPath.toString()));
			JSONObject jsonConfig = (JSONObject) configObject;

			JSONObject jsonGame = (JSONObject) jsonConfig.get("Game");
			JSONObject jsonPlayer = (JSONObject) jsonConfig.get("Player");
			JSONArray jsonBunkers = (JSONArray) jsonConfig.get("Bunkers");
			JSONArray jsonEnemies = (JSONArray) jsonConfig.get("Enemies");

			// Get the game settings
			JSONObject jsonGameSize = (JSONObject) jsonGame.get("size");
			this.gameX = ((Long) jsonGameSize.get("x")).intValue();
			this.gameY = ((Long) jsonGameSize.get("y")).intValue();

			// Get player settings
			String playerColour = (String) jsonPlayer.get("colour");
			Integer playerSpeed = ((Long) jsonPlayer.get("speed")).intValue();
			Integer playerLives = ((Long) jsonPlayer.get("lives")).intValue();
			JSONObject jsonPlayerPosition = (JSONObject) jsonPlayer.get("position");
			Integer playerX = ((Long) jsonPlayerPosition.get("x")).intValue();
			Integer playerY = ((Long) jsonPlayerPosition.get("y")).intValue();
			Vector2D playerPosition = new Vector2D(playerX, playerY);
			player = new Player(playerPosition, playerColour, playerSpeed, playerLives, new PlayerProjectileFactory(1));
			renderables.add(player);
			collidables.add(player);
			shootables.add(player);

			Builder bunkerBuilder = new BunkerBuiler();

			// Add bunkers
			for (Object bunkerObj : jsonBunkers) {
				JSONObject jsonBunker = (JSONObject) bunkerObj;
				JSONObject jsonBunkerPos = (JSONObject) jsonBunker.get("position");
				JSONObject jsonBunkerSize = (JSONObject) jsonBunker.get("size");

				Integer bunkerWidth = ((Long) jsonBunkerSize.get("x")).intValue();
				Integer bunkerHeight = ((Long) jsonBunkerSize.get("y")).intValue();
				Integer bunkerX = ((Long) jsonBunkerPos.get("x")).intValue();
				Integer bunkerY = ((Long) jsonBunkerPos.get("y")).intValue();

				// Build the bunker using the builder
				bunkerBuilder.reset();
				bunkerBuilder.addPos(bunkerX, bunkerY);
				bunkerBuilder.addSize(bunkerWidth, bunkerHeight);
				Bunker bunker = (Bunker) bunkerBuilder.getResultGameObj();
				gameobjects.add(bunker);
				renderables.add(bunker);
				collidables.add(bunker);
			}

			// Add enemies
			HiveMind hiveMind = new HiveMind(this.gameX);
			gameobjects.add(hiveMind);
			Builder alienBuilder = new AlienBuilder();
			ProjectileFactory enemyProjFact = new EnemyProjectileFactory(3);
			for (Object enemyObj : jsonEnemies){
				JSONObject jsonEnemy = (JSONObject) enemyObj;
				JSONObject jsonEnemyPos = (JSONObject) jsonEnemy.get("position");

				Integer enemyX = ((Long) jsonEnemyPos.get("x")).intValue();
				Integer enemyY = ((Long) jsonEnemyPos.get("y")).intValue();
				String projectileStrat = (String) jsonEnemy.get("projectile");

				// Build the alien using the builder
				alienBuilder.reset();
				alienBuilder.addPos(enemyX, enemyY);
				alienBuilder.addProjectileFact(enemyProjFact);

				Alien alien = (Alien) alienBuilder.getResultGameObj();
				alien.setProjectileStrat(projectileStrat);

				hiveMind.addAlien(alien);
				gameobjects.add(alien);
				renderables.add(alien);
				collidables.add(alien);
				shootables.add(alien);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the game/simulation
	 */
	public void update() {
		cleanup();
		movePlayer();
		registerProjectiles();
		for (GameObject go : gameobjects) {
			go.update();
		}

		checkCollisions();

		// ensure that renderable foreground objects don't go off-screen
		for (Renderable ro : renderables) {
			if (!ro.getLayer().equals(Renderable.Layer.FOREGROUND)) {
				continue;
			}
			if (ro.getPosition().getX() + ro.getWidth() >= gameX) {
				ro.getPosition().setX(gameX - 1 - ro.getWidth());
			}

			if (ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(1);
			}

			if (ro.getPosition().getY() + ro.getHeight() >= gameY) {
				ro.getPosition().setY(gameY - 1 - ro.getHeight());
			}

			if (ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}
	}

	/**
	 * Checks for collisions between collidable objects
	 */
	private void checkCollisions() {
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = i + 1; j < collidables.size(); j++) {
				Collidable c1 = collidables.get(i);
				Collidable c2 = collidables.get(j);

				if (c1.getCollider().isColliding(c2.getCollider())) {
					c1.onCollision(c2.getCollisionType());
					c2.onCollision(c1.getCollisionType());
				}
			}
		}
	}

	/**
	 * Cleans up the game objects that are no longer rendered
	 */
	private void cleanup() {
		List<Object> removing = new ArrayList<Object>();
		for (Renderable ro : renderables) {
			if (!ro.isRendered()) {
				removing.add(ro);
			}
		}

		for (Object o : removing) {
			renderables.remove(o);
			gameobjects.remove(o);
			collidables.remove(o);
			shootables.remove(o);
		}
	}

	/**
	 * Registers the projectiles that are released by the shootable objects
	 */
	private void registerProjectiles() {
		for (Shootable shooter : shootables){
			Projectile projectile = shooter.releaseProjectiles();
			if (projectile != null){
				gameobjects.add(projectile);
				renderables.add(projectile);
				collidables.add(projectile);
			}
		}
	}

	/**
	 * Returns the list of renderable objects
	 * @return the list of renderable objects
	 */
	public List<Renderable> getRenderables() {
		return renderables;
	}

	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased() {
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}

	public void rightPressed() {
		this.right = true;
	}

	public boolean shootPressed() {
		player.shoot();
		return true;
	}

	public Integer getGameX() {
		return this.gameX;
	}

	public Integer getGameY() {
		return this.gameY;
	}

	private void movePlayer() {
		if (left) {
			player.left();
		}

		if (right) {
			player.right();
		}
	}

	/**
	 * Checks if the game is still running, i.e. the player is still alive and there are still aliens left
	 * @return true if the game is still running, false otherwise
	 */
	public boolean isRunning(){
		if (!player.isAlive()){
			System.out.println("Player lost!");
			return false;
		}

		if (shootables.size() == 1){
			System.out.println("Player won!");
			return false;
		}

		return true;
	}
}

