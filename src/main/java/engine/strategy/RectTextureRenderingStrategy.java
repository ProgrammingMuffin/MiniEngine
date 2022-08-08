package engine.strategy;

import engine.Globals;
import engine.Renderer;
import engine.models.*;
import engine.services.GlService;
import engine.utils.ShaderUtil;

import java.io.InputStream;

public class RectTextureRenderingStrategy implements IRenderingStrategy {

    private int x;

    private int y;

    private int width;

    private int height;

    private String image;

    public RectTextureRenderingStrategy(StrategyParams params) {
        TextureRenderingStrategyParam param = params.textureStrategyParam;
        this.x = param.x;
        this.y = param.y;
        this.width = param.width;
        this.height = param.height;
        this.image = param.image;
    }

    @Override
    public void execute() {
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
