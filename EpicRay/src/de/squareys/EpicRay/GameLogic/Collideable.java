package de.squareys.EpicRay.GameLogic;

/**
 * An Interface for classes which handle collision.
 * 
 * @author Squareys
 * 
 */
public interface Collideable {
	/**
	 * On collision Handler
	 * 
	 * @param c
	 *            - the other partitipant in the collision
	 */
	public void onCollision(Collideable c);

	/**
	 * tests Collision with another Collideable
	 * 
	 * @param c
	 * @return
	 */
	public boolean isCollidingWidth(Collideable c);

}
