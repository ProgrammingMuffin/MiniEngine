package engine.strategy;

import engine.Globals;
import engine.models.BufferEnum;
import engine.models.ColoredPolygon;
import engine.models.DrawTypeEnum;
import engine.models.IRenderData;
import engine.models.Polygon;
import engine.models.RGBWrapper;
import engine.models.RenderTypeEnum;
import engine.models.ShaderProgram;
import engine.services.GlService;
import engine.utils.FileUtil;
import engine.utils.ShaderUtil;

public class PolygonColorRenderingStrategy implements IRenderingStrategy {

    @Override
    public void execute(IRenderData renderData) {
        if (!RenderTypeEnum.COLORED_POLYGON.equals(renderData.getRenderType())) {
            return;
        }
        ColoredPolygon coloredPoly = (ColoredPolygon) renderData;
        int camX = (Globals.camera == null ? 0 : Globals.camera.getX());
        int camY = (Globals.camera == null ? 0 : Globals.camera.getY());
        RGBWrapper rgb = coloredPoly.rgb;
        int polygons = coloredPoly.isDynamic() ? coloredPoly.dynPoints.size() : coloredPoly.points.size();
        if (polygons % 2 != 0 || polygons == 0) {
            throw new RuntimeException("There should be an even number of polygons");
        }
        float[] coordinates = new float[polygons];
        for (int i = 0; i < polygons; i += 2) {
            if (coloredPoly.isDynamic()) {
                coordinates[i] = coloredPoly.dynPoints.get(i).get() / Globals.screenWidth;
                coordinates[i + 1] = coloredPoly.dynPoints.get(i + 1).get() / Globals.screenHeight;
            } else {
                coordinates[i] = (float) coloredPoly.points.get(i) / Globals.screenWidth;
                coordinates[i + 1] = (float) coloredPoly.points.get(i + 1) / Globals.screenHeight;
            }
        }
        ShaderProgram shaderProgram;
        if (ShaderUtil.shaderMap.get("2d_simple_polygon_shader") == null) {
            shaderProgram = ShaderProgram.builder()
                    .addVertexShader(FileUtil.getFile("simple_shaders/2d_simple_vertex_shader.glsl"))
                    .addFragmentShader(FileUtil.getFile("simple_shaders/2d_simple_fragment_shader.glsl"))
                    .build();
            ShaderUtil.shaderMap.put("2d_simple_polygon_shader", shaderProgram);
        } else {
            shaderProgram = ShaderUtil.shaderMap.get("2d_simple_polygon_shader");
        }
        Polygon polygon = new Polygon();
        polygon.setCoordinates(coordinates);
        polygon.setRgb(rgb);
        GlService.renderObject(BufferEnum.SCENE_BUFFER, DrawTypeEnum.QUADS, polygon, 2, shaderProgram);
        GlService.resetRenderingContext();
    }
}
