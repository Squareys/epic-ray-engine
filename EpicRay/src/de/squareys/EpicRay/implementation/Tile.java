package de.squareys.EpicRay.implementation;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import de.squareys.EpicRay.framework.IEntity;
import de.squareys.EpicRay.framework.IGameFile;
import de.squareys.EpicRay.framework.IRenderingAttributes;
import de.squareys.EpicRay.framework.ISprite;
import de.squareys.EpicRay.framework.ITile;

public class Tile implements ITile {

	protected String m_typeID;
	
	protected Vector<IEntity> m_entities;
	protected Vector<ISprite> m_sprites;
	
	protected boolean m_opaque;
	protected boolean m_solid;
	
	protected IRenderingAttributes m_renderingAttribs;
	
	protected String m_name;
	
	Tile () {
		this("", false, false, null);
	}
	
	public Tile(String typeID, boolean opaque, boolean solid, IRenderingAttributes ra){
		m_typeID = typeID;
		
		m_entities = new Vector<IEntity>();
		m_sprites = new Vector<ISprite>();
		
		m_opaque = opaque;
		m_solid = solid;
		
		m_renderingAttribs = ra;
		
		m_name = "Tile";
	}
	
	public Tile(ITile tile, boolean fullcopy){
		m_typeID = tile.getTypeId();
		
		if (fullcopy){
			//TODO copy entities and sprites
		} else {
			m_entities = new Vector<IEntity>();
			m_sprites = new Vector<ISprite>();
		}
		
		m_opaque = tile.isOpaque();
		m_solid  = tile.isSolid();
		
		m_renderingAttribs = tile.getRenderingAttributes();
		
		m_name = tile.getName();
	}
	
	@Override
	public boolean saveToFile(IGameFile gameFile) throws IOException {
		gameFile.writeString(m_typeID);
		gameFile.writeString(m_name);
		
		gameFile.writeBoolean(m_opaque);
		gameFile.writeBoolean(m_solid);
		
		if (m_renderingAttribs == null){
			gameFile.writeBoolean(false);
		} else {
			gameFile.writeBoolean(true);
			gameFile.saveSaveable(m_renderingAttribs);
		}
		
		return true;
	}

	@Override
	public boolean loadFromFile(IGameFile gameFile) throws IOException {
		m_typeID = gameFile.readString();
		m_name 	 = gameFile.readString();
		
		m_opaque = gameFile.readBoolean();
		m_solid = gameFile.readBoolean();
		
		boolean hasRenderingAttribs = gameFile.readBoolean();
		
		if (hasRenderingAttribs){
			m_renderingAttribs = (IRenderingAttributes) gameFile.readSaveable();
		} else {
			m_renderingAttribs = null;
		}
		
		return true;
	}

	@Override
	public String getTag() {
		return "Tile";
	}

	@Override
	public boolean isOpaque() {
		return m_opaque;
	}

	@Override
	public boolean isSolid() {
		return m_solid;
	}

	@Override
	public String getName() {
		return m_name;
	}

	@Override
	public String getTypeId() {
		return m_typeID;
	}

	@Override
	public List<ISprite> getSprites() {
		return m_sprites;
	}

	@Override
	public List<IEntity> getEntities() {
		return m_entities;
	}

	@Override
	public void addEntity(IEntity entity) {
		m_entities.add(entity);
		entity.setCurrentTile(this);
	}

	@Override
	public void addSprite(ISprite sprite) {
		m_sprites.add(sprite);
	}
	
	@Override
	public IRenderingAttributes getRenderingAttributes() {
		return m_renderingAttribs;
	}

	@Override
	public void setRenderingAttributes(IRenderingAttributes ra) {
		m_renderingAttribs = ra;
	}

	@Override
	public void setOpaque(boolean b) {
		m_opaque = b;
	}

	@Override
	public void setSolid(boolean b) {
		m_solid = b;
	}

	@Override
	public void setName(String text) {
		m_name = text;
	}
	
	public boolean equals(Object o){
		if (o instanceof ITile){
			ITile t = (ITile) o;
			
			return getTypeId().equals(t.getTypeId());
		}
		return false;
	}

}
