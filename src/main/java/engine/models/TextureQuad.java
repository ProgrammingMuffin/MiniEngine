package engine.models;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import engine.services.AssetService;
import lombok.Builder;
import java.awt.image.BufferedImage;

@Builder
public class TextureQuad extends AbstractRenderData {

    public int x;

    public int y;

    public int width;

    public int height;

    public AtomicInteger dynX;

    public AtomicInteger dynY;

    public AtomicInteger dynWidth;

    public AtomicInteger dynHeight;

    public boolean dynamicPosition;

    public BufferedImage imageFile;

    public String image;

    public String assetId;

    public IPerPixelProcessing perPixelProcessing;

    @Override
    public RenderTypeEnum getRenderType() {
        return RenderTypeEnum.TEXTURE_QUAD;
    }

    @Override
    public Coordinates getCoordinates() {
        return Coordinates.builder()
                .x(x)
                .y(y)
                .build();
    }

    public static class TextureQuadBuilder {

        public TextureQuadBuilder loadAsset(InputStream imageFile) {
            this.imageFile = AssetService.getImage(imageFile);
            return this;
        }

        public TextureQuadBuilder loadAsset(BufferedImage bufferedImage) {
            this.imageFile = bufferedImage;
            return this;
        }
    }
}
