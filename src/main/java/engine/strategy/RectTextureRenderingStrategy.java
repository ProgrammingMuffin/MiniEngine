package engine.strategy;

import engine.Globals;
import engine.models.*;
import engine.services.GlService;
import engine.utils.FileUtil;
import engine.utils.ShaderUtil;

public class RectTextureRenderingStrategy implements IRenderingStrategy {

    public RectTextureRenderingStrategy() {
    }

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
        if (textureQuad.dynamicPosition) {
            x = (textureQuad.dynX != null) ? textureQuad.dynX.get() : x;
            y = (textureQuad.dynY != null) ? textureQuad.dynY.get() : y;
            width = (textureQuad.dynWidth != null) ? textureQuad.dynWidth.get() : width;
            height = (textureQuad.dynHeight != null) ? textureQuad.dynHeight.get() : height;
        }
        Coordinates relPos = renderData.getRelativeCoordinates();
        if (relPos != null) {
            x += relPos.x;
            y += relPos.y;
        }
        int camX = (Globals.camera == null ? 0 : Globals.camera.getX());
        int camY = (Globals.camera == null ? 0 : Globals.camera.getY());
        x = x - camX;
        y = y - camY;
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
        polygon.setPerPixelProcessing(textureQuad.perPixelProcessing);
        polygon.setAssetId(textureQuad.assetId);
        polygon.setTextureFile(textureQuad.imageFile);
        GlService.renderObject(BufferEnum.SCENE_BUFFER, DrawTypeEnum.QUADS, polygon, 2, shaderProgram);
        GlService.resetRenderingContext();
    }
}
