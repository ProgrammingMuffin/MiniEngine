package engine.utils;

import engine.Renderer;

import java.io.InputStream;

public class FileUtil {

    public static InputStream getFile(String fileName) {
        return Renderer.class.getClassLoader().getResourceAsStream(fileName);
    }
}
