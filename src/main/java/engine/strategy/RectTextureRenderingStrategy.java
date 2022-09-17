package engine.strategy;

import engine.Globals;
import engine.Renderer;
import engine.models.*;
import engine.services.GlService;
import engine.utils.FileUtil;
import engine.utils.ShaderUtil;

import java.io.InputStream;

public class RectTextureRenderingStrategy implements IRenderingStrategy {

    public RectTextureRenderingStrategy() {}

    @Override
    public void execute(IRenderData renderData) {
        if (!RenderTypeEnum.TEXTURE_QUAD.equals(renderData.getRenderType())) {
            return;
        }
        TextureQuad textureQuad = (TextureQuad) renderData;
        int x = textureQuad.x;
        int y = textureQuad.y;
        int width = textureQuad.width;
        int height = textureQuad.height;
        String image = textureQuad.image;
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
                    .addVertexShader(FileUtil.getFile("texture_shaders/2d_texture_vertex_shader.glsl"))
                    .addFragmentShader(FileUtil.getFile("texture_shaders/2d_texture_fragment_shader.glsl"))
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
}
