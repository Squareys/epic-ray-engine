package de.squareys.EpicRay.implementation;

import de.squareys.EpicRay.framework.Collideable;
import de.squareys.EpicRay.framework.IEntity;
import de.squareys.EpicRay.framework.ISprite;
import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.IWorld;

public class StaticEntity implements IEntity {
	protected ISprite m_sprite;
	protected IWorld m_world;
	protected ITile m_currentTile; //Tile the Entity is currently in
	
	protected double m_x;
	protected double m_y;
	
	protected int m_w;
	protected int m_h;

	protected double m_dirX;
	protected double m_dirY;
	
	public StaticEntity(int p_x, int p_y, int p_w, int p_h, ISprite sprite, IWorld world){
		m_x = p_x;
		m_y = p_y;
		
		m_w = p_w;
		m_h = p_h;
		
		m_sprite = sprite;
		m_world  = world;
		
		m_world.getTileMap().getTileAtPos(m_x, m_y).addEntity(this); //add the entity to an tile
	}
	@Override
	public boolean isCollidingWidth(Collideable c) {
		return false;
	}

	@Override
	public ISprite getSprite() {
		return m_sprite;
	}

	@Override
	public double getX() {
		return m_x;
	}

	@Override
	public double getY() {
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
	public IWorld getWorld() {
		return m_world;
	}

	@Override
	public ITile getCurrentTile() {
		return m_currentTile;
	}

	@Override
	public void onCollision(Collideable c) {
		//nothing will happen with this entity on collision
		
	}

	@Override
	public void onUpdate() {
		//do nothing, we're static!
		
	}
	
	@Override
	public void setCurrentTile(ITile tile) {
		m_currentTile = tile;
	}
	@Override
	public double getViewDirectionX() {
		return m_dirX;
	}
	@Override
	public double getViewDirectionY() {
		return m_dirY;
	}

}
