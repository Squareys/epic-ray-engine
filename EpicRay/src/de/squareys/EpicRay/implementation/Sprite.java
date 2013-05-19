package de.squareys.EpicRay.implementation;

import de.squareys.EpicRay.framework.ISprite;
import de.squareys.EpicRay.framework.ITexture;

/**
 * Simple implementation of ISprite
 * 
 * TODO: this is pretty static, unmoving
 * 
 * @author Squareys
 *
 */
public class Sprite implements ISprite {

	private int m_x;
	private int m_y;
	
	private int m_w;
	private int m_h;
	
	private ITexture m_texture;
	
	public Sprite (int p_x, int p_y, int p_w, int p_h, ITexture p_texture){
		m_x = p_x;
		m_y = p_y;
		
		m_w = p_w;
		m_h = p_h;
		
		m_texture = p_texture;
	}
	
	@Override
	public int getX() {
		return m_x;
	}

	@Override
	public int getY() {
		return m_y;
	}

	@Override
	public int getWidth() {
		return m_w;
	}

	@Override
	public int getHeight() {
		return m_h;
	}

	@Override
	public ITexture getTexture() {
		return m_texture;
	}

}
