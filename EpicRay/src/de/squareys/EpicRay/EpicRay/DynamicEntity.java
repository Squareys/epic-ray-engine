package de.squareys.EpicRay.EpicRay;

import de.squareys.EpicRay.GameLogic.Collideable;
import de.squareys.EpicRay.GameLogic.ITile;
import de.squareys.EpicRay.GameLogic.IWorld;
import de.squareys.EpicRay.Rendering.ISprite;

public class DynamicEntity extends StaticEntity {

	protected float m_vx;
	protected float m_vy;
	
	protected float m_speed;
	
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
	public void onUpdate(float timeDiff) {
		float oldX = m_x;
		float oldY = m_y;
		
		m_x += m_vx * m_speed * timeDiff;
		m_y += m_vy * m_speed * timeDiff;
		
		ITile tile = m_world.getTileMap().getTileAtPos(m_x, m_y);
		
		
		if (tile.isSolid()){
			//prevent movement
			m_x = oldX;
			m_y = oldY;
		} else {
			tile.addEntity(this); //update the current Tile
		}
	}
	
	/**
	 * Set multiples of length of movement dir vector this Entity travels per second. (TODO)
	 * @param speed
	 */
	public void setSpeed(float speed){
		m_speed = speed;
	}
	
	public void setMovementDir(float vx, float vy){
		m_vx = vx;
		m_vy = vy;
	}
}
