package Entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import models.ModelWithTexture;
import org.lwjgl.util.vector.Vector3f;

@AllArgsConstructor
@Getter
@Setter
public class Entity {

    private ModelWithTexture model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
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

    public  void increaseScale(float s){
        this.scale += s;
    }


}
