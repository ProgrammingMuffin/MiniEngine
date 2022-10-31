package engine.services;

import java.util.HashMap;

import engine.models.Scene;

public class SceneManager {

    private final HashMap<String, Scene> sceneMap = new HashMap<>();

    private String currentScene = null;

    public void registerScene(String sceneCode, Scene scene) {
        this.sceneMap.put(sceneCode, scene);
    }

    public void startScene(String sceneCode) {
        this.currentScene = sceneCode;
    }

    public Scene getCurrentScene() {
        if (currentScene == null) {
            return null;
        }
        if (this.sceneMap.get(currentScene) == null) {
            throw new RuntimeException("Scene '" + currentScene + "' isn't registered");
        }
        return this.sceneMap.get(currentScene);
    }
}
