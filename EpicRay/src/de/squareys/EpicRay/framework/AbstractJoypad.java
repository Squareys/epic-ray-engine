package de.squareys.EpicRay.framework;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Abstract Joypad
 * 
 * Class for KeyboardInputAbstraction. Uses Singleton model, so
 * keep in mind when deriving!
 * 
 * @author Squareys
 *
 */
public abstract class AbstractJoypad implements AWTEventListener {
	
	protected int[] m_keyValues; //real values for the action Keys
	protected KeyState[] m_keyStates; //states of the keys
	
	protected static AbstractJoypad m_instance;

	//KeyState Enum
	public enum KeyState {
		KS_DOWN, KS_NONE, KS_UP, KS_PRESSED
	}
	
	public static KeyState getKeyState(int actionKey){
		return KeyState.KS_NONE;
	}
	
	public static AbstractJoypad getInstance() {
		return m_instance;	
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		int keyIndex = findKeyIndex(keyCode);
		
		if (keyIndex != -1){
			KeyState prev = m_keyStates[keyIndex];
			
			if (prev.equals(KeyState.KS_DOWN)){
				m_keyStates[keyIndex] = KeyState.KS_PRESSED;
			} else {
				m_keyStates[keyIndex] = KeyState.KS_DOWN;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		int keyIndex = findKeyIndex(keyCode);
		
		if (keyIndex != -1){
			KeyState prev = m_keyStates[keyIndex];
			
			if (prev.equals(KeyState.KS_UP)){
				m_keyStates[keyIndex] = KeyState.KS_NONE;
			} else {
				m_keyStates[keyIndex] = KeyState.KS_UP;
			}
		}
	}
	
	public void eventDispatched(AWTEvent event) {
        if (event instanceof KeyEvent){
        	if (event.getID() == KeyEvent.KEY_PRESSED){
        		keyPressed((KeyEvent) event);
        		return;
        	} else if ( event.getID() == KeyEvent.KEY_RELEASED){
        		keyReleased((KeyEvent) event);
        		return;
        	}
        }
    }
	
	
	/**
	 * Returns true, if keyCode is used in Joypad
	 * Faster than (findKeyIndex(keyCode) == -1)
	 * @param keyCode
	 * @return
	 */
	public boolean keyInJoypad(int keyCode){
		for (int val : m_keyValues){
			if ( val == keyCode){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the array index of the given keyCode.
	 * Returns -1 if keyCode was not found.
	 * @param keyCode
	 * @return
	 */
	public int findKeyIndex(int keyCode){
		for ( int i = 0; i < m_keyValues.length; i++){
			if (keyCode == m_keyValues[i]) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns state of actionKey
	 * @param actionKey
	 * @return
	 */
	public KeyState key(int actionKey){
		return m_keyStates[actionKey];
	}
	
	public boolean keyIsDown(int actionKey){
		KeyState state = m_keyStates[actionKey];
		return ( state == KeyState.KS_DOWN || state == KeyState.KS_PRESSED);
	}
}
