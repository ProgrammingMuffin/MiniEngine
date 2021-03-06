package engine.strategy;

import engine.Globals;
import engine.Renderer;
import engine.models.BufferEnum;
import engine.models.DrawTypeEnum;
import engine.models.ShaderProgram;
import engine.models.Polygon;
import engine.services.GlService;
import engine.utils.ShaderUtil;

import java.io.InputStream;
import java.net.URISyntaxException;

public class RectTextureRenderingStrategy {

    private int x;

    private int y;

    private int width;

    private int height;

    private String image;

    public RectTextureRenderingStrategy(int x, int y, int width, int height, String image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void execute() throws URISyntaxException {
        float[] coordinates = {
                (float) x / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) (y + height) / Globals.screenHeight,
                (float) x / Globals.screenWidth, (float) (y + height) / Globals.screenHeight
        };
        float[] textureCoordinates = {
                0f, 1f,
                1f, 1f,
                1f, 0f,
                0f, 0f
        };
        ShaderProgram shaderProgram;
        if (ShaderUtil.shaderMap.get("2d_texture_shader") == null) {
            shaderProgram = ShaderProgram.builder()
                    .addVertexShader(getFile("texture_shaders/2d_texture_vertex_shader.glsl"))
                    .addFragmentShader(getFile("texture_shaders/2d_texture_fragment_shader.glsl"))
                    .build();
            ShaderUtil.shaderMap.put("2d_texture_shader", shaderProgram);
        } else {
            shaderProgram = ShaderUtil.shaderMap.get("2d_texture_shader");
        }
        Polygon polygon = new Polygon();
        polygon.setCoordinates(coordinates);
        polygon.setTextureCoordinates(textureCoordinates);
        polygon.setTextureFileName(image);
        GlService.renderObject(BufferEnum.SCENE_BUFFER, DrawTypeEnum.QUADS, polygon, 2, shaderProgram);
        GlService.resetRenderingContext();
    }

    private InputStream getFile(String fileName) {
            return Renderer.class.getClassLoader().getResourceAsStream(fileName);
    }
}
