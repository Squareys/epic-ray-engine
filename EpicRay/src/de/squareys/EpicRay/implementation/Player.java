package de.squareys.EpicRay.implementation;

import de.squareys.EpicRay.framework.ISprite;
import de.squareys.EpicRay.framework.IWorld;

public class Player extends DynamicEntity {
	protected int m_spin = 0;
	protected double m_spinSpeed;

	
	public static final int DIR_FORWARD = 0;
	public static final int DIR_BACKWARD = 1;
	
	public Player(int p_x, int p_y, int p_w, int p_h, ISprite sprite,
			IWorld world) {
		super(p_x, p_y, p_w, p_h, sprite, world);

		m_dirX = -1.0;
		m_dirY = 0.0;
		
		m_speed = 0.2;
		m_spinSpeed = 0.02;
	}

	public void setSpin(int spin) {
		m_spin = spin;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if (m_spin != 0) {
			double oldDirX = m_dirX;
			m_dirX = m_dirX * Math.cos(m_spin * m_spinSpeed) - m_dirY
					* Math.sin(m_spin * m_spinSpeed);
			m_dirY = oldDirX * Math.sin(m_spin * m_spinSpeed) + m_dirY
					* Math.cos(m_spin * m_spinSpeed);
			
			double l = Math.sqrt(m_dirX * m_dirX + m_dirY * m_dirY);
			
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
