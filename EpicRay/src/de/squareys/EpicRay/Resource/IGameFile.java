package de.squareys.epicray.resource;

import java.io.IOException;
import java.util.List;

/**
 * Saves Saveables.
 * 
 * @author Squareys
 * 
 */
public interface IGameFile {

	public String readString() throws IOException;

	public boolean writeString(String s) throws IOException;

	public int readInt() throws IOException;

	public boolean writeInt(int i) throws IOException;

	public float readFloat() throws IOException;

	public boolean writeFloat(float f) throws IOException;

	public char readChar() throws IOException;

	public boolean writeChar(char c) throws IOException;

	public boolean readBoolean() throws IOException;

	public boolean writeBoolean(boolean b) throws IOException;

	public List readList() throws IOException;

	public boolean writeList(List l) throws IOException;

	public Saveable readSaveable() throws IOException;

	public boolean saveSaveable(Saveable o) throws IOException;

}
