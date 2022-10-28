package engine.models;

import lombok.Builder;

@Builder
public class TextureQuad implements IRenderData {

    public int x;

    public int y;

    public int width;

    public int height;

    public String image;

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
}