package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

/**
 * This class does everything related to initialization stuff
 */
public class Init {

    public static long window;

    public static void init () {
        GLFW.glfwInit();
        window = GLFW.glfwCreateWindow(640, 480, "hello world!", GLFW.glfwGetPrimaryMonitor(), 0);
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback();
    }
}
