package engine.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coordinates {

    public Integer x;

    public Integer y;

    public Integer z;

    public Integer rx;

    public Integer ry;

    public Integer rz;

}
