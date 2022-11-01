package engine.models;

// Marker interface
public interface IRenderData {

    void setRelativeTo(IRenderData renderData);

    Coordinates getRelativeCoordinates();

    RenderTypeEnum getRenderType();

    Coordinates getCoordinates();
}
