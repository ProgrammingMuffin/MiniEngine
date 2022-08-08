package engine.models;

import lombok.Data;

@Data
public class AbstractCamera {

    /**
     * X coordinate of the camera viewport
     */
    protected int x;

    /**
     * Y coordinate of the camera viewport
     */
    protected int y;

    /**
     * Z coordinate of the camera viewport
     */
    protected int z;

    /**
     * X rotation of the camera viewport
     */
    protected int rx;

    /**
     * Y rotation of the camera viewport
     */
    protected int ry;
}
