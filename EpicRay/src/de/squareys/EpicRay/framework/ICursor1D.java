package de.squareys.EpicRay.framework;

import java.util.Iterator;

/**
 * ICursor1D 
 * A one dimensional cursor for high performance
 * work on array storage.
 * 
 * @author Squareys
 * @param <T> Type of the values in the Bitmap
 */
public interface ICursor1D <T> extends Iterable<T>, Iterator<T> {

	/**
	 * Value at the cursors position.
	 * @return
	 */
	public T get();
	
	/**
	 * Set the value at the cursors position
	 * @param value
	 */
	public void set(T value);
	
	/**
	 * Move the cursor to the next position and get()
	 */
	public T next();
	
	/**
	 * Move the cursor to the next position
	 */
	public void fwd();
	
	/**
	 * Set the cursors position.
	 * Note: This does not check for out of bounds.
	 * @param index
	 */
	public void setPosition(int index);
	
	/**
	 * Returns the cursors index position.
	 * @return
	 */
	public int getPosition();

	/**
	 * Copy the Cursor
	 * @return
	 */
	public ICursor1D<T> copy();

	/**
	 * Move cursor back a position
	 */
	public void bck();
	
	/**
	 * bck() and get()
	 */
	public T prev();
}
