package de.squareys.EpicRay.Rendering;

import de.squareys.EpicRay.Bitmap.IBitmap;

/**
 * IRenderer Interface for Renderers
 * 
 * 
 * @author Squareys
 * 
 */
public interface IRenderer<B extends IBitmap> {

	/**
	 * Renders everything to a bitmap
	 * 
	 * @param world
	 *            to render
	 */
	public void render();

	/**
	 * Returns the rendered bitmap
	 * 
	 * @return
	 */
	public B getRenderResult();
}
