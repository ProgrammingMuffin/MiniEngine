package engine;

import engine.eventHandlers.KeyPressEventHandler;
import engine.models.AbstractCamera;
import engine.models.BufferEnum;
import engine.services.SceneManager;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Globals {

    public static int screenWidth = 1024;

    public static int screenHeight = 768;

    public static boolean fullscreen = false;

    public static Map<BufferEnum, Integer> bufferMap = new HashMap<>();

    public static Map<BufferEnum, Integer> arrayMap = new HashMap<>();

    // Keys are file names. Values are OpenGL generated texture IDs
    public static Map<String, Integer> textureMap = new HashMap<>();

    public static AbstractCamera camera;

    public static KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler();

    public static HashMap<String, ByteBuffer> assetCache = new HashMap<>();
    
    public static String windowTitle = "Hello World!";
    
    public static SceneManager sceneManager;
}
