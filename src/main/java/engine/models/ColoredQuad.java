package engine.models;

import lombok.Builder;

@Builder
public class ColoredQuad implements IRenderData {

    public int x;

    public int y;

    public int width;

    public int height;

    public RGBWrapper rgb;

    @Override
    public RenderTypeEnum getRenderType() {
        return RenderTypeEnum.COLORED_QUAD;
    }
}
