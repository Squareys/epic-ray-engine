package de.squareys.EpicRay.implementation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import de.squareys.EpicRay.framework.IGameFile;
import de.squareys.EpicRay.framework.Saveable;

public class GameFile implements IGameFile {

	DataInputStream m_inputStream;
	DataOutputStream m_outputStream;
	
	String m_filename;
	
	boolean m_input = false;
	boolean m_output = false;
	
	public GameFile(String filename) {
		m_filename = filename;
		
		 
	}

	@Override
	public String readString() throws IOException {
		return m_inputStream.readUTF();
	}

	@Override
	public boolean writeString(String s) throws IOException {
		m_outputStream.writeUTF(s);
		return true;
	}

	@Override
	public int readInt() throws IOException {
		return m_inputStream.readInt();
	}

	@Override
	public boolean writeInt(int i) throws IOException {
		m_outputStream.writeInt(i);
		return true;
	}

	@Override
	public float readFloat() throws IOException {
		return m_inputStream.readFloat();
	}

	@Override
	public boolean writeFloat(float f) throws IOException {
		m_outputStream.writeFloat(f);
		return true;
	}

	@Override
	public char readChar() throws IOException {
		return m_inputStream.readChar();
	}

	@Override
	public boolean writeChar(char c) throws IOException {
		m_outputStream.writeChar(c);
		return true;
	}

	@Override
	public boolean readBoolean() throws IOException {
		return m_inputStream.readBoolean();
	}

	@Override
	public boolean writeBoolean(boolean b) throws IOException {
		m_outputStream.writeBoolean(b);
		return true;
	}

	@Override
	public List readList() throws IOException {
		// TODO 
		return null;
	}

	@Override
	public boolean writeList(List l) throws IOException {
		// TODO 
		return false;
	}
	
	public void prepareForSaving(){
		File file = new File(m_filename);
		
		if (!file.exists()) {
			m_input = false;
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("Error in File Creation GameFile");
			}
		}
		
		//Create DataOutputStream
		m_output = true;
		try {
			FileOutputStream outFile = new FileOutputStream(file);
			m_outputStream = new DataOutputStream(outFile);
		} catch (FileNotFoundException e) {
			m_output = false;
		}
	}
	
	public void prepareForLoading(){
		File file = new File(m_filename);
		
		if (file.exists()){
			FileInputStream inFile;
			m_input = true;
			
			try {
				inFile = new FileInputStream(file);
				m_inputStream = new DataInputStream(inFile);
			} catch (FileNotFoundException e) {
				m_input = false;
			}
		}
	}

	@Override
	public Saveable readSaveable() throws IOException {
		if (!m_input){
			prepareForLoading();
			
			if (!m_input){
				//still not working!
				return null;
			}
		}
		
		String tag = readString();
		
		Saveable object = null;
		
		if (tag.equals("TileMap")){
			object = new TileMap();
			
			object.loadFromFile(this);
		} else if (tag.equals("Tile")){
			object = new Tile();
			
			object.loadFromFile(this);
		} else if (tag.equals("ERRAttribs")){
			object = new EpicRayRenderingAttributes();
			
			object.loadFromFile(this);
		} else {
			throw new RuntimeException("Unknown tag in GameFile.readSaveable!!");
		}
		
		return object;
	}

	@Override
	public boolean saveSaveable(Saveable o) throws IOException {
		if (!m_output){
			prepareForSaving();
			
			if (!m_output){
				//still not working!
				return false;
			}
		}
		
		if (o == null) {
			return false;
		}
		
		writeString(o.getTag());
		o.saveToFile(this);
		
		return true;
	}

}
