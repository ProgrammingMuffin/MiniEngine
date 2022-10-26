package engine.strategy;

import engine.Globals;
import engine.models.*;
import engine.services.GlService;
import engine.utils.FileUtil;
import engine.utils.ShaderUtil;

public class RectGradientRenderingStrategy implements IRenderingStrategy {

    @Override
    public void execute(IRenderData renderData) {
        if (!RenderTypeEnum.GRADIENT_QUAD.equals(renderData.getRenderType())) {
            return;
        }
        GradientQuad gradientQuad = (GradientQuad) renderData;
        int camX = (Globals.camera == null ? 0 : Globals.camera.getX());
        int camY = (Globals.camera == null ? 0 : Globals.camera.getY());
        int x = gradientQuad.x - camX;
        int y = gradientQuad.y - camY;
        int width = gradientQuad.width;
        int height = gradientQuad.height;
        RGBWrapper rgb = gradientQuad.rgb;
        float[] coordinates = {
                (float) x / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) (y + height) / Globals.screenHeight,
                (float) x / Globals.screenWidth, (float) (y + height) / Globals.screenHeight
        };
        ShaderProgram shaderProgram;
        if (ShaderUtil.shaderMap.get("2d_gradient_shader") == null) {
            shaderProgram = ShaderProgram.builder()
                    .addVertexShader(FileUtil.getFile("gradient_shaders/2d_gradient_vertex_shader.glsl"))
                    .addFragmentShader(FileUtil.getFile("gradient_shaders/2d_gradient_fragment_shader.glsl"))
                    .build();
            ShaderUtil.shaderMap.put("2d_gradient_shader", shaderProgram);
        } else {
            shaderProgram = ShaderUtil.shaderMap.get("2d_gradient_shader");
        }
        Polygon polygon = new Polygon();
        polygon.setCoordinates(coordinates);
        polygon.setRgb(rgb);
        GlService.renderObject(BufferEnum.SCENE_BUFFER, DrawTypeEnum.QUADS, polygon, 2, shaderProgram);
        GlService.resetRenderingContext();
    }
}
