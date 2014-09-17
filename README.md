EpicRay
=======
A Modern Raycasting Engine in Java


If you are interested in helping me with this project, be free to contact me via twitter, facebook or tumblr. Or just send a pull request.

This is a "true" raycasting engine. What that is supposed to mean is that the rendering code actually uses raycasting techniques and doesn't just try to immitate the look. An editor comes along in the code aswell.

Be aware that this project is still in its early phases and may never be completed. There will be "API-breaks" and I will not try to avoid them too much.

Technical
--------

The description says "Modern" raycasting engine. This is because I am looking to implement more contemprorary effects and filters in EpicRay (DOF maybe?). Nobody knows if I will ever get to it, though.

For floor rendering I used my own algorithm. I wanted to be able to have seperate textures/colors for each tile without having to cast a ray for each pixel. This, of course, kindof conflicts with the "trueness" of the raycasting.

Features
--------

The current features are implemented at the moment
- Engine:
  - Wall rendering: textured or single color
  - Floor/Ceiling Rendering: textured or single color
  - Keyboard movement and world collision
  - zBuffer (makes way for fog and masked texture implementation)
  - Threaded Rendering
- Editor: 
  - Basic tilemap editor
  - Basic tilecreator/editor
  - Loading and saving
  - Play the game directly in the editor! :)

Todos
-----

Here are some todos you could do, if you are thinking of contributing:
- Engine:
  - Sprite Rendering
  - Player spawn
  - Mouse movement
  - Postprocessing framework and effects (fog, dof, ...)
  - Background image
  - Lighting, maybe?
- Editor:
  - Resource manager dialog for textures and other assets
  - Player spawn
  - Background image
  - Better data/functionality/gui separation

Contact
=======

My blog: http://squareys.tumblr.com/

My twitter: @squareys

My facebook: http://www.facebook.com/squareys
