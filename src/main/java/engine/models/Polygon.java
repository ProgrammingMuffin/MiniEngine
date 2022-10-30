package engine.models;

import lombok.Data;

@Data
public class Polygon {

    private float[] coordinates;

    private float[] textureCoordinates;

    private String textureFileName;

    private String assetId;

    private IPerPixelProcessing perPixelProcessing;

    private RGBWrapper rgb;
}
