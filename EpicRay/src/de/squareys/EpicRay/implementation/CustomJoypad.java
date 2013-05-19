package de.squareys.EpicRay.implementation;

import java.awt.event.KeyEvent;

import de.squareys.EpicRay.framework.AbstractJoypad;

public class CustomJoypad extends AbstractJoypad {
	
	public static final int KEY_FORWARD = 0;
	public static final int KEY_BACKWARD = 1;
	public static final int KEY_TURN_LEFT = 2;
	public static final int KEY_TURN_RIGHT = 3;
	
	private final int numActionKeys = 4;
	
	private CustomJoypad(){
		m_keyValues = new int[numActionKeys];
		m_keyStates = new KeyState[numActionKeys];
		
		for (int i = 0; i < numActionKeys; i++){
			m_keyStates[i] = KeyState.KS_NONE; //initialize with KS_NONE
		}
		
		m_keyValues[KEY_FORWARD] = KeyEvent.VK_W;
		m_keyValues[KEY_BACKWARD] = KeyEvent.VK_S;
		m_keyValues[KEY_TURN_LEFT] = KeyEvent.VK_A;
		m_keyValues[KEY_TURN_RIGHT] = KeyEvent.VK_D;
	}

	public static AbstractJoypad getInstance(){
		if ( m_instance == null) {
			return (m_instance = new CustomJoypad());
		} else {
			return m_instance;
		}
	}

}
