package engine.utils;

import engine.models.ShaderProgram;
import org.lwjgl.opengl.GL30;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ShaderUtil {

    public static final Map<String, ShaderProgram> shaderMap = new HashMap<>();

    private static int compileAndAddShaderStage(String shaderName, File file) {
        StringBuilder source = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line);
                source.append("\n");
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        compileShader()
    }

    private static int compileShader(int type, String source) {
        int shader = GL30.glCreateShader(type);
        GL30.glShaderSource(shader, source);
        GL30.glCompileShader(shader);
        System.out.println("GL errors: " + GL30.glGetError());
        return shader;
    }
}
