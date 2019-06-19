package Engine.Entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Engine.models.ModelWithTexture;
import org.lwjgl.util.vector.Vector3f;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Entity {

    private ModelWithTexture model;
    private Vector3f position;
    private float rotX;
    private float rotY;
    private float rotZ;
    private float scale;

    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
    public void increaseRotation(float dx, float dy, float dz){
        this.rotX+= dx;
        this.rotY += dy;
        this.rotZ+= dz;
    }

    public void resetHigh(){
        this.position.y = 0f;
    }

    public  void increaseScale(float s){
        this.scale += s;
    }


}
