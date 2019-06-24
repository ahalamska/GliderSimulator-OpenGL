package engine.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;

@Setter
@Getter
@AllArgsConstructor
public class Light {
    private Vector3f position;
    private Vector3f colour;
}
