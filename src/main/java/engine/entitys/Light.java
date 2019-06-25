package engine.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;

@Setter
@Getter
@AllArgsConstructor

public class Light {
    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenuation;

    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
        this.attenuation = new Vector3f(1,0,0);
    }

    public void move(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
}
