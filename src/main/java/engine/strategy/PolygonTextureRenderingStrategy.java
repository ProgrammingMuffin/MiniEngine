package engine.strategy;

import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.assimp.AIImporterDesc.Buffer;

import engine.models.IRenderData;
import engine.models.Polygon;
import engine.models.RenderTypeEnum;
import engine.models.ShaderProgram;
import engine.models.TexturePolygon;
import engine.Globals;
import engine.models.BufferEnum;
import engine.models.DrawTypeEnum;
import engine.services.GlService;
import engine.utils.FileUtil;
import engine.utils.ShaderUtil;

public class PolygonTextureRenderingStrategy implements IRenderingStrategy {

    @Override
    public void execute(IRenderData renderData) {
        if (!RenderTypeEnum.TEXTURE_POLYGON.equals(renderData.getRenderType())) {
            return;
        }
        TexturePolygon texturePoly = (TexturePolygon) renderData;
        int camX = (Globals.camera == null ? 0 : Globals.camera.getX());
        int camY = (Globals.camera == null ? 0 : Globals.camera.getY());
        String image = texturePoly.image;
        int polygons = texturePoly.isDynamic() ? texturePoly.dynPoints.size() : texturePoly.points.size();
        if (polygons % 2 != 0 || polygons == 0) {
            throw new RuntimeException("There should be an even number of polygons");
        }
        float[] coordinates = new float[polygons];
        for (int i = 0; i < polygons; i += 2) {
            if (texturePoly.isDynamic()) {
                coordinates[i] = (float) (texturePoly.dynPoints.get(i).get() - camX) / Globals.screenWidth;
                coordinates[i + 1] = (float) (texturePoly.dynPoints.get(i + 1).get() - camY) / Globals.screenHeight;
            } else {
                coordinates[i] = (float) (texturePoly.points.get(i) - camX) / Globals.screenWidth;
                coordinates[i + 1] = (float) (texturePoly.points.get(i + 1) - camY) / Globals.screenHeight;
            }
        }
        float[] textureCoordinates = {
                1f, 1f,
                0f, 1f,
                0f, 0f,
                1f, 0f
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
        polygon.setPerPixelProcessing(texturePoly.perPixelProcessing);
        polygon.setAssetId(texturePoly.assetId);
        polygon.setTextureFile(texturePoly.imageFile);
        GlService.renderObject(BufferEnum.SCENE_BUFFER, DrawTypeEnum.QUADS, polygon, 2, shaderProgram);
        GlService.resetRenderingContext();
    }
}
