package engine.strategy;

import engine.Globals;
import engine.Renderer;
import engine.models.BufferEnum;
import engine.models.DrawTypeEnum;
import engine.models.Polygon;
import engine.models.ShaderProgram;
import engine.services.GlService;
import engine.utils.ShaderUtil;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

public class RectColorRenderingStrategy {

    private int x;

    private int y;

    private int width;

    private int height;

    public RectColorRenderingStrategy(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void execute() throws URISyntaxException {
        float[] coordinates = {
                (float) x / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) (y + height) / Globals.screenHeight,
                (float) x / Globals.screenWidth, (float) (y + height) / Globals.screenHeight
        };
        ShaderProgram shaderProgram;
        if (ShaderUtil.shaderMap.get("2d_simple_shader") == null) {
            shaderProgram = ShaderProgram.builder()
                    .addVertexShader(getFile("simple_shaders/2d_simple_vertex_shader.glsl"))
                    .addFragmentShader(getFile("simple_shaders/2d_simple_fragment_shader.glsl"))
                    .build();
            ShaderUtil.shaderMap.put("2d_simple_shader", shaderProgram);
        } else {
            shaderProgram = ShaderUtil.shaderMap.get("2d_simple_shader");
        }
        Polygon polygon = new Polygon();
        polygon.setCoordinates(coordinates);
        GlService.renderObject(BufferEnum.SCENE_BUFFER, DrawTypeEnum.QUADS, polygon, 2, shaderProgram);
        GlService.resetRenderingContext();
    }

    private InputStream getFile(String fileName) {
            return Renderer.class.getClassLoader().getResourceAsStream(fileName);
    }
}
