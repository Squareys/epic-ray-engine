package de.squareys.EpicRay.Resource;

public interface ISound extends IResource<ISound> {

	/**
	 * play the Sound
	 */
	public void play();

	/**
	 * stop the Sound
	 */
	public void stop();

	/**
	 * set volume
	 * 
	 * @param volume
	 */
	public void setVolume(float volume);

	/**
	 * set panning of Sound from -1.0 (left) to 0.0 (center) to 1.0 (right)
	 * 
	 * @param panning
	 */
	public void setPanning(float panning);

}
