package engine.models;

import java.nio.ByteBuffer;

public interface IPerPixelProcessing {

    void process(ByteBuffer byteBuffer, int pixelRow, int pixelCol, byte r, byte g, byte b, byte a);
}
