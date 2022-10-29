package engine.strategy;

import java.util.ArrayList;
import java.util.HashMap;

import engine.models.Hybrid;
import engine.models.IRenderData;
import engine.models.RenderTypeEnum;

public class HybridRenderingStrategy implements IRenderingStrategy {

    public HybridRenderingStrategy() {
    }

    @Override
    public void execute(IRenderData renderData) {
        if (!RenderTypeEnum.HYBRID.equals(renderData.getRenderType())) {
            return;
        }
        Hybrid hybrid = (Hybrid) renderData;
        HashMap<Integer, ArrayList<IRenderData>> renderables = (hybrid.renderables == null) ? new HashMap<>()
                : hybrid.renderables;
        for (Integer level : renderables.keySet()) {
            for (IRenderData component : renderables.get(level)) {
                component.getRenderType().getRenderingStrategy().execute(component);
            }
        }
    }
}
