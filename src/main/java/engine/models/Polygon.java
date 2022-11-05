package engine.models;

import java.io.InputStream;

import lombok.Data;
import java.awt.image.BufferedImage;

@Data
public class Polygon {

    private float[] coordinates;

    private float[] textureCoordinates;

    private BufferedImage textureFile;

    private String textureFileName;

    private String assetId;

    private IPerPixelProcessing perPixelProcessing;

    private RGBWrapper rgb;
}
