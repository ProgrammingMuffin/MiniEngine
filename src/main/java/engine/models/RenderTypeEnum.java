package engine.models;

import engine.strategy.IRenderingStrategy;
import engine.strategy.RectColorRenderingStrategy;
import engine.strategy.RectTextureRenderingStrategy;

public enum RenderTypeEnum {

    COLORED_QUAD {
        public IRenderingStrategy getStrategy(StrategyParams params) {
            return new RectColorRenderingStrategy(params);
        }
    },
    TEXTURED_QUAD {
        public IRenderingStrategy getStrategy(StrategyParams params) {
            return new RectTextureRenderingStrategy(params);
        }
    };

    public abstract IRenderingStrategy getStrategy(StrategyParams params);
}
