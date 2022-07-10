import engine.Init;
import engine.strategy.RectColorRenderingStrategy;
import engine.strategy.RectTextureRenderingStrategy;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.net.URISyntaxException;

public class Application {

    public static void main(String[] args) {
        System.out.println("Running game");
        Init.init();
        RectColorRenderingStrategy rect1 = new RectColorRenderingStrategy(0, 0, 50, 50);
        RectTextureRenderingStrategy rect2 = new RectTextureRenderingStrategy(-100, 100, 300, 300, "doge.jpg");
        while (true) {
            try {
                GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
                rect1.execute();
                rect2.execute();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            GLFW.glfwSwapBuffers(Init.window);
            GLFW.glfwPollEvents();
        }
    }
}
