package engine;

import engine.models.BufferEnum;

import java.util.HashMap;
import java.util.Map;

public class Globals {

    public static int screenWidth = 640;

    public static int screenHeight = 480;

    public static Map<BufferEnum, Integer> bufferMap = new HashMap<>();

    public static Map<BufferEnum, Integer> arrayMap = new HashMap<>();

    // Keys are file names. Values are OpenGL generated texture IDs
    public static Map<String, Integer> textureMap = new HashMap<>();
}
