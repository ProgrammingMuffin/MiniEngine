package engine.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RGBWrapper {

    int r;

    int g;

    int b;

    int a;

    public RGBWrapper(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public static RGBWrapper of(int r, int g, int b) {
        return new RGBWrapper(r, g, b);
    }

    public static RGBWrapper of(int r, int g, int b, int a) {
        return new RGBWrapper(r, g, b, a);
    }
}
