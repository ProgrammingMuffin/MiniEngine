package engine.models;

import engine.strategy.IRenderingStrategy;
import engine.strategy.RectColorRenderingStrategy;
import engine.strategy.RectGradientRenderingStrategy;
import engine.strategy.RectTextureRenderingStrategy;
import lombok.Getter;

public enum RenderTypeEnum {

    COLORED_QUAD (new RectColorRenderingStrategy()),
    TEXTURE_QUAD (new RectTextureRenderingStrategy()),
    GRADIENT_QUAD (new RectGradientRenderingStrategy());

    RenderTypeEnum(IRenderingStrategy renderingStrategy) {
        this.renderingStrategy = renderingStrategy;
    }

    @Getter
    private IRenderingStrategy renderingStrategy;
}
