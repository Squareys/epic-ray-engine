package de.squareys.EpicRay.framework;

/**
 * TODO: Incomplete and Bad
 * 
 * @author Squareys
 * 
 */
public interface IInputHandler {

	public class KeyEvent {

	}

	public class MouseEvent {
		int x, y;

		enum type {
			TYPE_MOUSE_MOVE, TYPE_MOUSE_RCLICK, TYPE_MOUSE_LCLICK, TYPE_RBTN_DOWN, TYPE_RBTN_UP, TYPE_LBTN_DOWN, TYPE_LBTN_UP, TYPE_SCROLL_UP, TYPE_SCROLL_DOWN, TYPE_SCROLLBTN
		}
	}
}
