package engine.models;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Builder;
import lombok.Getter;

@Builder
public class ColoredPolygon extends AbstractRenderData {

    public ArrayList<Integer> points;

    public ArrayList<AtomicInteger> dynPoints;

    public RGBWrapper rgb;

    @Getter
    public boolean dynamic;

    @Override
    public RenderTypeEnum getRenderType() {
        return RenderTypeEnum.COLORED_POLYGON;
    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    public static class ColoredPolygonBuilder {

        public ColoredPolygonBuilder addPoint(int x, int y) {
            if (this.points == null) {
                this.points = new ArrayList<>();
            }
            this.points.add(x);
            this.points.add(y);
            return this;
        }
    }

}
