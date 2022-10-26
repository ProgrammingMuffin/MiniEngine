package engine.models;

import lombok.Builder;

@Builder
public class GradientQuad implements IRenderData {

    public int x;

    public int y;

    public int width;

    public int height;

    public RGBWrapper rgb;

    @Override
    public RenderTypeEnum getRenderType() {
        return RenderTypeEnum.GRADIENT_QUAD;
    }

    @Override
    public Coordinates getCoordinates() {
        System.out.println("LOLOL: " + this.x + " and y is: " + this.y);
        return Coordinates.builder()
                .x(this.x)
                .y(this.y)
                .build();
    }
}
