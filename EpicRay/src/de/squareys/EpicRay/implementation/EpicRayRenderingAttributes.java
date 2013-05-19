package de.squareys.EpicRay.implementation;

import java.io.IOException;

import de.squareys.EpicRay.framework.*;

public class EpicRayRenderingAttributes implements IRenderingAttributes {
	public int m_wallColor;
	public int m_floorColor;
	public int m_ceilColor;
	
	public boolean m_textured;
	
	public ITexture m_wallTexture;
	public ITexture m_floorTexture;
	public ITexture m_ceilTexture;

	public EpicRayRenderingAttributes(){
		m_wallColor = -1;
		m_floorColor = -1;
		m_ceilColor = -1;
		
		m_textured = false;
		
		m_wallTexture = null;
		m_floorTexture = null;
		m_ceilTexture = null;
	}
	
	public EpicRayRenderingAttributes(EpicRayRenderingAttributes ra) {
		m_wallColor = ra.m_wallColor;
		m_floorColor = ra.m_floorColor;
		m_ceilColor = ra.m_ceilColor;
		
		m_textured = ra.m_textured;
		
		m_wallTexture = ra.m_wallTexture;
		m_floorTexture = ra.m_floorTexture;
		m_ceilTexture = ra.m_ceilTexture;
	}

	public ITexture getWallTexture(){
		return m_wallTexture;
	}
	
	public ITexture getFloorTexture(){
		return m_floorTexture;
	}
	
	public ITexture getCeilingTexture(){
		return m_ceilTexture;
	}
	
	@Override
	public boolean saveToFile(IGameFile gameFile) throws IOException {
		gameFile.writeInt(m_wallColor);
		gameFile.writeInt(m_floorColor);
		gameFile.writeInt(m_ceilColor);
		
		gameFile.writeBoolean(m_textured);
		return true;
	}

	@Override
	public boolean loadFromFile(IGameFile gameFile) throws IOException {
		m_wallColor = gameFile.readInt();
		m_floorColor = gameFile.readInt();
		m_ceilColor = gameFile.readInt();
		
		m_textured = gameFile.readBoolean();
		return true;
	}

	@Override
	public String getTag() {
		return "ERRAttribs";
	}
}
