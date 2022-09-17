import engine.Init;
import engine.models.*;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {
        System.out.println("Running game");
        Init.init();
        ArrayList<IRenderData> coloredRectangles = new ArrayList<>();
        coloredRectangles.add(GradientQuad.builder().x(-400).y(-400)
                .width(400).height(400).rgb(new RGBWrapper(120, 150, 120)).build());
        coloredRectangles.add(ColoredQuad.builder().x(100).y(0)
                .width(250).height(150).rgb(new RGBWrapper(0, 255, 180)).build());
        coloredRectangles.add(TextureQuad.builder().x(-100).y(100)
                .width(300).height(300).image("doge.jpg").build());
        while (true) {
            coloredRectangles.forEach(rectangle -> {
                rectangle.getRenderType().getRenderingStrategy().execute(rectangle);
            });
            GLFW.glfwSwapBuffers(Init.window);
            GLFW.glfwPollEvents();
        }
    }
}
