package engine.models;

import java.nio.ByteBuffer;

public interface IPerPixelProcessing {

    void process(ByteBuffer byteBuffer, int pixelRow, int pixelCol, int r, int g, int b, int a);
}
