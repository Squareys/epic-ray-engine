package de.squareys.epicray.cursor;

import java.util.Iterator;

/**
 * ICursor1D A one dimensional cursor for high performance work on array
 * storage.
 * 
 * @author Squareys
 * @param <T>
 *            Type of the values in the Bitmap
 */
public interface ICursor1D<T> extends Iterable<T>, Iterator<T> {

	/**
	 * Value at the cursors position.
	 * 
	 * @return
	 */
	public T get();

	/**
	 * Set the value at the cursors position
	 * 
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
	 * Move the cursor n positions forward
	 */
	public void fwd(int n);

	/**
	 * Set the cursors position relative to offset. 
	 * Note: This does not check for out of bounds.
	 * 
	 * @param index
	 */
	public void setPosition(int index);
	
	/**
	 * Set the cursors position. 
	 * Note: This does not check for out of bounds.
	 * 
	 * @param index
	 */
	public void setAbsolutePosition(int index);

	/**
	 * Returns the cursors index position.
	 * 
	 * @return
	 */
	public int getPosition();
	
	/**
	 * Returns the cursors position relative to offset
	 */
	public int getRelativePosition();

	/**
	 * Copy the Cursor
	 * 
	 * @return
	 */
	public ICursor1D<T> copy();

	/**
	 * Move cursor back a position
	 */
	public void bck();

	/**
	 * Move the cursor n positions back
	 */
	public void bck(int n);

	/**
	 * bck() and get()
	 */
	public T prev();

	/**
	 * reset position to offset
	 */
	public void reset();
	
	/**
	 * Set the offset of the cursor to reset to at current position
	 */
	public void setOffset();
	
	/**
	 * Get the offset of the cursor
	 */
	public int getOffset();
}
