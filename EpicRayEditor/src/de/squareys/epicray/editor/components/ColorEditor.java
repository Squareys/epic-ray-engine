package de.squareys.epicray.editor.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorEditor extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4545422081297164391L;

	private Color m_color;
	
	public ColorEditor(int initRGB){
		m_color = new Color(initRGB);
		
		setPreferredSize(new Dimension(80, 20));
		
		addMouseListener(this);
	}
	
	public void setColor(Color col){
		m_color = col;
		repaint();
	}
	
	public Color getColor(){
		return m_color;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//TODO: m_color can be null after next line
		m_color = JColorChooser.showDialog(
                this, "Choose Background Color",
                m_color);
		
		fireChangedEvent();
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	Vector<ChangeListener> m_listeners = new Vector<ChangeListener>();
	
	public void addChangeListener(ChangeListener l){
		if (m_listeners.contains(l)) return;
		m_listeners.add(l);
	}
	
	public void fireChangedEvent(){
		for (ChangeListener l : m_listeners){
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(m_color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
