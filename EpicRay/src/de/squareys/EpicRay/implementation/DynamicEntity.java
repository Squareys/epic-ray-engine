package de.squareys.EpicRay.implementation;

import de.squareys.EpicRay.framework.Collideable;
import de.squareys.EpicRay.framework.ISprite;
import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.IWorld;

public class DynamicEntity extends StaticEntity {

	protected double m_vx;
	protected double m_vy;
	
	protected double m_speed;
	
	
	public DynamicEntity(int p_x, int p_y, int p_w, int p_h, ISprite sprite, IWorld world) {
		super(p_x, p_y, p_w, p_h, sprite, world);
	}
	
	@Override
	public void onCollision(Collideable c) {
		//stop the entity
		m_vx = 0;
		m_vy = 0;
	}

	@Override
	public void onUpdate() {
		double oldX = m_x;
		double oldY = m_y;
		
		m_x += m_vx * m_speed;
		m_y += m_vy * m_speed;
		
		ITile tile = m_world.getTileMap().getTileAtPos(m_x, m_y);
		
		
		if (tile.isSolid()){
			//prevent movement
			m_x = oldX;
			m_y = oldY;
		} else {
			tile.addEntity(this); //update the current Tile
		}
	}
	
	public void setSpeed(double speed){
		m_speed = speed;
	}
	
	public void setMovementDir(double vx, double vy){
		m_vx = vx;
		m_vy = vy;
	}
}
