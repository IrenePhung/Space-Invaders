package invaders.logic;

/**
 * The interface all damagable objects must implement
 */
public interface Damagable {
	/**
	 * Represents the object taking damage
	 * @param amount the amount of damage to take
	 */
	public void takeDamage(double amount);

	/**
	 * Represents the object current health
	 * @return the health of the object
	 */
	public double getHealth();

	/**
	 * Checks if the object is alive
	 * @return true if the object is alive, false otherwise
	 */
	public boolean isAlive();

}

