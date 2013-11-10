package de.squareys.EpicRay.Resource;

public interface IResource<T> {
	/**
	 * Load resource from a file
	 * 
	 * @param filename
	 * @return
	 */
	public T loadFromFile(String filename);
}
