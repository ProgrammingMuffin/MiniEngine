package engine.models;

import engine.strategy.IRenderingStrategy;
import engine.strategy.PolygonColorRenderingStrategy;
import engine.strategy.PolygonTextureRenderingStrategy;
import engine.strategy.RectColorRenderingStrategy;
import engine.strategy.RectGradientRenderingStrategy;
import engine.strategy.RectTextureRenderingStrategy;
import engine.strategy.HybridRenderingStrategy;
import lombok.Getter;

public enum RenderTypeEnum {

    COLORED_QUAD(new RectColorRenderingStrategy()),
    TEXTURE_QUAD(new RectTextureRenderingStrategy()),
    GRADIENT_QUAD(new RectGradientRenderingStrategy()),
    COLORED_POLYGON(new PolygonColorRenderingStrategy()),
    TEXTURE_POLYGON(new PolygonTextureRenderingStrategy()),
    HYBRID(new HybridRenderingStrategy());

    RenderTypeEnum(IRenderingStrategy renderingStrategy) {
        this.renderingStrategy = renderingStrategy;
    }

    @Getter
    private IRenderingStrategy renderingStrategy;
}
