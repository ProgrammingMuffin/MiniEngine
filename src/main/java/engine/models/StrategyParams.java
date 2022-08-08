package engine.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StrategyParams {

    private RenderTypeEnum renderType;

    private ColorRenderingStrategyParam colorStrategyParam;

    private TextureRenderingStrategyParam textureStrategyParam;

    public void setColorRenderingStrategyParam(ColorRenderingStrategyParam param) {
        this.renderType = RenderTypeEnum.COLORED_QUAD;
        this.colorStrategyParam = param;
    }
}
