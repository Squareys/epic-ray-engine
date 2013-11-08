package de.squareys.EpicRay.framework;

/**
 * Rendering Attributes Interface
 * 
 * Needs to be defined for an specific Renderer. Instances are owned by tiles
 * and other renderable entities
 * 
 * Rendering Attributes could for example specify Diffuse, Ambient and Specular
 * color and intensity.
 * 
 * @author Squareys
 * 
 */

public interface IRenderingAttributes extends Saveable<IRenderingAttributes> {

}
