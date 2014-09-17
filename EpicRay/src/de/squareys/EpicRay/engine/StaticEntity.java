package de.squareys.epicray.engine;

import de.squareys.epicray.gamelogic.Collideable;
import de.squareys.epicray.gamelogic.IEntity;
import de.squareys.epicray.gamelogic.ITile;
import de.squareys.epicray.gamelogic.IWorld;
import de.squareys.epicray.rendering.ISprite;

public class StaticEntity implements IEntity {
	protected ISprite m_sprite;
	protected IWorld m_world;
	protected ITile m_currentTile; //Tile the Entity is currently in
	
	protected float m_x;
	protected float m_y;
	
	protected int m_w;
	protected int m_h;

	protected float m_dirX;
	protected float m_dirY;
	
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
	public float getX() {
		return m_x;
	}

	@Override
	public float getY() {
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
	public void onUpdate(float timeDiff) {
		//do nothing, we're static!
	}
	
	@Override
	public void setCurrentTile(ITile tile) {
		m_currentTile = tile;
	}
	
	@Override
	public float getViewDirectionX() {
		return m_dirX;
	}
	
	@Override
	public float getViewDirectionY() {
		return m_dirY;
	}

}
