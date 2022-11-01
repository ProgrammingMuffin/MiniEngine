package engine.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Hybrid extends AbstractRenderData {

    public int x;

    public int y;

    public int z;

    public int rx;

    public int ry;

    // This is of the form <z-index/level, the object to render>
    // Things inside renderables will have a positioning relative to the hybrid's
    // position
    @Getter
    private final Map<Integer, ArrayList<IRenderData>> renderables = new TreeMap<>();

    @Override
    public RenderTypeEnum getRenderType() {
        return RenderTypeEnum.HYBRID;
    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    public void addComponent(int level, IRenderData component) {
        this.renderables.putIfAbsent(level, new ArrayList<>());
        this.renderables.get(level).add(component);
    }
}
