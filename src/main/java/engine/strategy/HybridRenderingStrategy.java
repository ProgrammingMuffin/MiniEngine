package engine.strategy;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
        Map<Integer, ArrayList<IRenderData>> renderables = (hybrid.getRenderables() == null) ? new TreeMap<>()
                : hybrid.getRenderables();
        for (Integer level : renderables.keySet()) {
            for (IRenderData component : renderables.get(level)) {
                component.getRenderType().getRenderingStrategy().execute(component);
            }
        }
    }
}
