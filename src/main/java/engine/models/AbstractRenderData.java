package engine.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractRenderData implements IRenderData {

    protected Coordinates relativeCoordinates;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    protected boolean relative;

    @Override
    public void setRelativeTo(IRenderData renderData) {
        this.relativeCoordinates = renderData.getCoordinates();
        setRelative(true);
    }

    @Override
    public Coordinates getRelativeCoordinates() {
        if (!isRelative()) {
            return null;
        }
        return this.relativeCoordinates;
    }
}
