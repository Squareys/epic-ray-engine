package de.squareys.EpicRay.Cursor;

import de.squareys.EpicRay.Util.Tuple;

public interface ICursor2D<T> extends ICursor1D<T> {

	public void nextX();

	public void nextY();

	public void setPosition(int x, int y);

	public Tuple<Integer, Integer> getPositionXY();
}
