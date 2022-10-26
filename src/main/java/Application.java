import engine.Globals;
import engine.Init;
import engine.eventHandlers.KeyPressEventHandler;
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
        GradientQuad player = getPlayer(-400);
        coloredRectangles.add(player);
        coloredRectangles.add(ColoredQuad.builder().x(100).y(0)
                .width(250).height(150).rgb(new RGBWrapper(0, 255, 180)).build());
        coloredRectangles.add(TextureQuad.builder().x(-100).y(100)
                .width(300).height(300).image("doge.jpg").build());
        Globals.keyPressEventHandler.registerEvent(GLFW.GLFW_KEY_W, () -> {
            GradientQuad oldPlayer = (GradientQuad) coloredRectangles.get(0);
            coloredRectangles.set(0, getPlayer(oldPlayer.y + 2));
        });
        while (running.get()) {
            AttachableCamera camera = new AttachableCamera();
            camera.attachObject(coloredRectangles.get(0));
            Globals.camera = camera;
            ((AttachableCamera) Globals.camera).updatePosition();
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
            System.out.println("Camera x: " + camera.getAttachedObject().getCoordinates().x + " and Camera y: "
                    + Globals.camera.getY());
            coloredRectangles.forEach(rectangle -> {
                rectangle.getRenderType().getRenderingStrategy().execute(rectangle);
            });
            GLFW.glfwSwapBuffers(Init.window);
            GLFW.glfwPollEvents();
            Globals.keyPressEventHandler.poll(Init.window);
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
