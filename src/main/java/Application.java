import engine.Globals;
import engine.Init;
import engine.models.*;
import engine.services.AssetService;
import engine.services.StatService;
import engine.utils.AwtUtil;
import engine.utils.FileUtil;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Application {

    public static void main(String[] args) {
        final AtomicBoolean running = new AtomicBoolean(true);
        System.out.println("Running game");
        Init.init();
        ArrayList<IRenderData> coloredRectangles = new ArrayList<>();
        coloredRectangles.add(ColoredPolygon.builder().addPoint(100, 200).addPoint(-100, 50).addPoint(50, -200)
                .addPoint(200, 200).addPoint(100, 200).rgb(RGBWrapper.of(255, 255, 255)).dynamic(false).build());
        coloredRectangles.add(TexturePolygon.builder()
                .addPoint(new AtomicInteger(0), new AtomicInteger(500))
                .addPoint(new AtomicInteger(500), new AtomicInteger(500))
                .addPoint(new AtomicInteger(500), new AtomicInteger(0))
                .addPoint(new AtomicInteger(0), new AtomicInteger(0))
                .dynamic(true)
                .assetId("doge")
                .loadAsset(AssetService.getImage(Application.class.getClassLoader().getResourceAsStream("doge.jpg")))
                .build());
        AtomicInteger frames = new AtomicInteger(0);
        while (running.get()) {
            if (frames.get() == 0 || frames.get() == 1) {
                StatService.printFps(frames);
            }
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
            frames.incrementAndGet();
        }
    }

    public static GradientQuad getPlayer(int x) {
        return GradientQuad.builder().x(-400).y(x)
                .width(400).height(400).rgb(new RGBWrapper(120, 150, 120)).build();
    }
}
