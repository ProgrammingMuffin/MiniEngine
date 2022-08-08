package engine.strategy;

import engine.Globals;
import engine.Renderer;
import engine.models.*;
import engine.services.GlService;
import engine.utils.ShaderUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@NoArgsConstructor
public class RectColorRenderingStrategy implements IRenderingStrategy {

    private int x;

    private int y;

    private int width;

    private int height;

    private RGBWrapper rgb;

    public RectColorRenderingStrategy(StrategyParams params) {
        ColorRenderingStrategyParam param = params.colorStrategyParam;
        this.x = param.x;
        this.y = param.y;
        this.width = param.width;
        this.height = param.height;
        this.rgb = param.rgb;
    }

    @Override
    public void execute() {
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
