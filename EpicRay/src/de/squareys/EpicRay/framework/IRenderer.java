package de.squareys.EpicRay.framework;

/**
 * IRenderer Interface for Renderers
 * 
 * 
 * @author Squareys
 *
 */
public interface IRenderer {

	/**
	 * Renders everything to a 
	 * bitmap
	 * @param world to render
	 */
	public void render();

	/**
	 * Returns the rendered bitmap
	 * @return
	 */
	public IBitmap getRenderResult();
}
