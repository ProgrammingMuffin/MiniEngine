package engine.models;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Builder;

@Builder
public class Hybrid implements IRenderData {

    public int x;

    public int y;

    public int z;

    public int rx;

    public int ry;

    // This is of the form <z-index/level, the object to render>
    // Things inside renderables will have a positioning relative to the hybrid's
    // position
    public final HashMap<Integer, ArrayList<IRenderData>> renderables = new HashMap<>();

    @Override
    public RenderTypeEnum getRenderType() {
        return RenderTypeEnum.HYBRID;
    }

    @Override
    public Coordinates getCoordinates() {
        // TODO Auto-generated method stub
        return null;
    }

}
