import engine.Globals;
import engine.Init;
import engine.models.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {

    public static void main(String[] args) {
        final AtomicBoolean running = new AtomicBoolean(true);
        System.out.println("Running game");
        Init.init();
        ArrayList<IRenderData> coloredRectangles = new ArrayList<>();
        coloredRectangles.add(TextureQuad.builder().x(-100).y(100)
                .width(300).height(300).image("doge.jpg").build());
        while (running.get()) {
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
            coloredRectangles.forEach(rectangle -> {
                rectangle.getRenderType().getRenderingStrategy().execute(rectangle);
            });
            GLFW.glfwSwapBuffers(Init.window);
            GLFW.glfwPollEvents();
            GLFW.glfwSetWindowCloseCallback(Init.window, window -> {
                GLFW.glfwDestroyWindow(window);
                running.set(false);
            });
            if (running.get() == false) {
                break;
            }
        }
    }

    public static GradientQuad getPlayer(int x) {
        return GradientQuad.builder().x(-400).y(x)
                .width(400).height(400).rgb(new RGBWrapper(120, 150, 120)).build();
    }
}
