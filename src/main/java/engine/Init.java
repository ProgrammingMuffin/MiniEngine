package engine;

import engine.models.BufferEnum;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;

/**
 * This class does everything related to initialization stuff
 */
public class Init {

    public static long window;

    public static void init () {
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GL30.GL_TRUE);
        window = GLFW.glfwCreateWindow(Globals.screenWidth, Globals.screenHeight, "hello world!", 0, 0);
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback();
        initVertexBufferObjects();
    }

    private static void initVertexBufferObjects() {
        Globals.bufferMap.put(BufferEnum.SCENE_BUFFER, GL30.glGenBuffers());
        Globals.bufferMap.put(BufferEnum.OBJECT_BUFFER, GL30.glGenBuffers());
        Globals.bufferMap.put(BufferEnum.TEXTURE_BUFFER, GL30.glGenBuffers());
        Globals.arrayMap.put(BufferEnum.SCENE_BUFFER, GL30.glGenVertexArrays());
        Globals.arrayMap.put(BufferEnum.OBJECT_BUFFER, GL30.glGenVertexArrays());
        Globals.arrayMap.put(BufferEnum.TEXTURE_BUFFER, GL30.glGenVertexArrays());
    }
}
