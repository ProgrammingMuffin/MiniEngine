import engine.Init;
import engine.models.*;
import engine.services.StatService;

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
        Hybrid hybrid = Hybrid.builder()
                .x(-100)
                .y(-100)
                .build();
        Hybrid hybrid2 = Hybrid.builder()
                .x(-100)
                .y(-100)
                .build();
        hybrid.renderables.put(1, new ArrayList<>());
        java.awt.Polygon mugInside = new java.awt.Polygon();
        mugInside.addPoint(30, 83);
        mugInside.addPoint(81, 82);
        mugInside.addPoint(76, 89);
        mugInside.addPoint(35, 89);
        mugInside.addPoint(30, 83);
        java.awt.Polygon mugOutside = new java.awt.Polygon();
        mugOutside.addPoint(21, 87);
        mugOutside.addPoint(8, 100);
        mugOutside.addPoint(9, 160);
        mugOutside.addPoint(26, 170);
        mugOutside.addPoint(88, 169);
        mugOutside.addPoint(104, 160);
        mugOutside.addPoint(104, 92);
        mugOutside.addPoint(92, 83);
        mugOutside.addPoint(81, 95);
        mugOutside.addPoint(32, 96);
        mugOutside.addPoint(21, 87);
        byte r1 = -127;
        byte g1 = -127;
        byte b1 = 0;
        byte a1 = -127;
        byte r2 = -127;
        byte g2 = 0;
        byte b2 = 0;
        byte a2 = -127;
        byte r3 = -50;
        byte g3 = 100;
        byte b3 = -50;
        byte a3 = -127;
        TextureQuad texQuad = TextureQuad.builder().x(-100).y(-100).width(150).height(150)
                .perPixelProcessing((byteBuffer, pixelRow, pixelCol, r, g, b, a) -> {
                    System.out.println("row: " + pixelRow + ", col: " + pixelCol);
                    if (mugInside.contains(pixelRow, pixelCol)) {
                        byteBuffer.put(r1).put(g1).put(b1).put(a1);
                    } else if (mugOutside.contains(pixelRow, pixelCol)) {
                        byteBuffer.put(r2).put(g2).put(b2).put(a2);
                    } else {
                        byteBuffer.put(r).put(g).put(b).put(a);
                    }
                }).image("coffee.png").assetId("coffee1").build();
        TextureQuad texQuad2 = TextureQuad.builder().x(100).y(100).width(150).height(150)
                .perPixelProcessing((byteBuffer, pixelRow, pixelCol, r, g, b, a) -> {
                    System.out.println("row: " + pixelRow + ", col: " + pixelCol);
                    if (mugInside.contains(pixelRow, pixelCol)) {
                        byteBuffer.put(r1).put(g1).put(b1).put(a1);
                    } else if (mugOutside.contains(pixelRow, pixelCol)) {
                        byteBuffer.put(r3).put(g3).put(b3).put(a3);
                    } else {
                        byteBuffer.put(r).put(g).put(b).put(a);
                    }
                }).image("coffee.png").assetId("coffee2").build();
        hybrid.renderables.get(1)
                .add(texQuad);
        hybrid2.renderables.put(1, new ArrayList<>());
        hybrid2.renderables.get(1).add(texQuad2);
        AtomicInteger frames = new AtomicInteger(0);
        coloredRectangles.add(hybrid);
        coloredRectangles.add(hybrid2);
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
