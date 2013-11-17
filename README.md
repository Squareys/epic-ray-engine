EpicRay
=======
A Modern Raycasting Engine in Java


If you are interested in helping me with this project, be free to contact me via twitter, facebook or tumblr.

This is a "true" raycasting engine. Meaning, the rendering code actually uses raycasting techniques and doesn't just try to produce a look-alike result. An editor comes along in the code aswell.

Technical
--------

The description says "Modern" raycasting engine. This is becaus I am looking to implement more contemprorary effects and filters in EpicRay (DOF maybe?).

Recently I had the idea to make EpicRay optionally use hardware accelleration. Who knows, maybe one day :)

For floor rendering I used my own "algorithm". I wanted to be able to have seperate textures/colors for each tile without having to cast a ray for each pixel. My way may be a speedup to the classic way.

Features
--------

The current features are implemented at the moment
- Engine:
  - Wall rendering: textured or single color
  - Floor/Ceiling Rendering: texture or single color
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
  - Post-Effects (fog, ...) [will do a basic system myself first]
  - Background image
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
