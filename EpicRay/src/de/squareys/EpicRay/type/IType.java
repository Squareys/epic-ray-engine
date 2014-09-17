package de.squareys.epicray.type;

public interface IType<T> {

	public T[] createArray(int size);
	
	public T get();
	public void set(T value);
}
