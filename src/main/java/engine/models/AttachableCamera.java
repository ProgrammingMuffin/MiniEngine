package engine.models;

import lombok.Getter;

public class AttachableCamera extends AbstractCamera {

    @Getter
    private boolean attached;

    @Getter
    private IRenderData attachedObject;

    public void attachObject(IRenderData objectToAttach) {
        this.attachedObject = objectToAttach;
        this.attached = true;
    }

    public IRenderData detachObject() {
        IRenderData detachedObject = this.getAttachedObject();
        this.attachedObject = null;
        this.attached = false;
        return detachedObject;
    }

    public void updatePosition() {
        if (!this.isAttached()) {
            return;
        }
        Coordinates coordinates = attachedObject.getCoordinates();
        this.setX(coordinates.x);
        this.setY(coordinates.y);
    }
}
