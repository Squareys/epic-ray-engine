package de.squareys.epicray.engine;

import de.squareys.epicray.gamelogic.IWorld;
import de.squareys.epicray.rendering.ISprite;

public class Player extends DynamicEntity {
	protected int m_spin = 0;
	protected float m_spinSpeed;

	
	public static final int DIR_FORWARD = 0;
	public static final int DIR_BACKWARD = 1;
	
	public Player(int p_x, int p_y, int p_w, int p_h, ISprite sprite,
			IWorld world) {
		super(p_x, p_y, p_w, p_h, sprite, world);

		m_dirX = -1.0f;
		m_dirY = 0.0f;
		
		m_speed = 2.0f;
		m_spinSpeed = 0.01f;
	}

	public void setSpin(int spin) {
		m_spin = spin;
	}

	@Override
	public void onUpdate(float timeDiff) {
		super.onUpdate(timeDiff);
		
		if (m_spin != 0) {
			float spin = m_spin * m_speed * timeDiff;
			
			float oldDirX = m_dirX;
			m_dirX = m_dirX * (float) Math.cos(spin) - m_dirY
					* (float) Math.sin(spin);
			m_dirY = oldDirX * (float) Math.sin(spin) + m_dirY
					* (float) Math.cos(spin);
			
			float l = (float) Math.sqrt(m_dirX * m_dirX + m_dirY * m_dirY);
			
			m_dirX /= l; //just in case, normalize
			m_dirY /= l;
		}
	}
	
	public void setLocalMovementDir(int direction){
		if ( direction == DIR_FORWARD){
			m_vx = m_dirX * m_speed;
			m_vy = m_dirY * m_speed;
			
			return;
		}
		
		if ( direction == DIR_BACKWARD){
			m_vx = -m_dirX * m_speed;
			m_vy = -m_dirY * m_speed;
			
			return;
		}
	}

}
