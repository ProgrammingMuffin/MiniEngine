package engine.models;

/**
 * A scene is just a state of displaying things. If a scene is stopped and
 * another scene is picked, new elements are supposed to appear or basically the
 * state of rendering changes to something else and also its related
 * functionality.
 * Something like "Oh I am in main menu scene. Oh Now I am resuming back to the
 * game, so I am in the game scene and so all UI elements of main menu will stop
 * now and new set of code will start executing"
 */
public interface Scene {

    public void render();

    public void update();

    public void clear();
}
