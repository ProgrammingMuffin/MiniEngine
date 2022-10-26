package engine.eventHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

public class KeyPressEventHandler {

    public HashMap<Integer, ArrayList<Runnable>> events = new HashMap<>();

    public void registerEvent(int key, Runnable runnable) {
        this.events.putIfAbsent(key, new ArrayList<>());
        this.events.get(key).add(runnable);
    }

    public void poll(long window) {
        for (int key : events.keySet()) {
            if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                for (Runnable runnable : events.get(key)) {
                    runnable.run();
                }
            }
        }
    }
}
