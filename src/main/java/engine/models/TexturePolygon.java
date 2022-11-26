package engine.models;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.image.BufferedImage;

import engine.services.AssetService;
import lombok.Builder;
import lombok.Getter;

@Builder
public class TexturePolygon extends AbstractRenderData {

    public ArrayList<Integer> points;

    public ArrayList<AtomicInteger> dynPoints;

    @Getter
    public boolean dynamic;

    public BufferedImage imageFile;

    public String image;

    public String assetId;

    public IPerPixelProcessing perPixelProcessing;

    @Override
    public RenderTypeEnum getRenderType() {
        return RenderTypeEnum.TEXTURE_POLYGON;
    }

    @Override
    public Coordinates getCoordinates() {
    	Coordinates coords = new Coordinates();
    	coords.x = dynamic ? this.dynPoints.get(0).get() : this.points.get(0);
    	coords.y = dynamic ? this.dynPoints.get(1).get() : this.points.get(1);
        return coords;
    }

    public static class TexturePolygonBuilder {

        public TexturePolygonBuilder addPoint(int x, int y) {
            if (this.points == null) {
                this.points = new ArrayList<>();
            }
            this.points.add(x);
            this.points.add(y);
            return this;
        }

        public TexturePolygonBuilder addPoint(AtomicInteger x, AtomicInteger y) {
            if (this.dynPoints == null) {
                this.dynPoints = new ArrayList<>();
            }
            this.dynPoints.add(x);
            this.dynPoints.add(y);
            return this;
        }

        public TexturePolygonBuilder loadAsset(InputStream imageFile) {
            this.imageFile = AssetService.getImage(imageFile);
            return this;
        }

        public TexturePolygonBuilder loadAsset(BufferedImage bufferedImage) {
            this.imageFile = bufferedImage;
            return this;
        }
    }

}
