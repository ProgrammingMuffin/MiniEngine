package engine.strategy;

import engine.Globals;
import engine.models.*;
import engine.services.GlService;
import engine.utils.FileUtil;
import engine.utils.ShaderUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RectColorRenderingStrategy implements IRenderingStrategy {

    @Override
    public void execute(IRenderData renderData) {
        if (!RenderTypeEnum.COLORED_QUAD.equals(renderData.getRenderType())) {
            return;
        }
        ColoredQuad coloredQuad = (ColoredQuad) renderData;
        int x = coloredQuad.x;
        int y = coloredQuad.y;
        Coordinates relPos = renderData.getRelativeCoordinates();
        if (relPos != null) {
            x += relPos.x;
            y += relPos.y;
        }
        int camX = (Globals.camera == null ? 0 : Globals.camera.getX());
        int camY = (Globals.camera == null ? 0 : Globals.camera.getY());
        x = x - camX;
        y = y - camY;
        int width = coloredQuad.width;
        int height = coloredQuad.height;
        RGBWrapper rgb = coloredQuad.rgb;
        float[] coordinates = {
                (float) x / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth, (float) (y + height) / Globals.screenHeight,
                (float) x / Globals.screenWidth, (float) (y + height) / Globals.screenHeight
        };
        ShaderProgram shaderProgram;
        if (ShaderUtil.shaderMap.get("2d_simple_shader") == null) {
            shaderProgram = ShaderProgram.builder()
                    .addVertexShader(FileUtil.getFile("simple_shaders/2d_simple_vertex_shader.glsl"))
                    .addFragmentShader(FileUtil.getFile("simple_shaders/2d_simple_fragment_shader.glsl"))
                    .build();
            ShaderUtil.shaderMap.put("2d_simple_shader", shaderProgram);
        } else {
            shaderProgram = ShaderUtil.shaderMap.get("2d_simple_shader");
        }
        Polygon polygon = new Polygon();
        polygon.setCoordinates(coordinates);
        polygon.setRgb(rgb);
        GlService.renderObject(BufferEnum.SCENE_BUFFER, DrawTypeEnum.QUADS, polygon, 2, shaderProgram);
        GlService.resetRenderingContext();
    }
}
