package engine.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RGBWrapper {

    int r;

    int g;

    int b;

    public static RGBWrapper of(int r, int g, int b) {
        return new RGBWrapper(r, g, b);
    }
}
