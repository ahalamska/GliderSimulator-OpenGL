package engine.entitys;

import engine.terrains.Terrain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import engine.models.ModelWithTexture;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

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


    protected Terrain getCurrentTerrain(List<Terrain> terrains) {

        for(Terrain terrain : terrains){
            if(position.x >= terrain.getX() && (position.x <= terrain.getX() + Terrain.SIZE)){
                if(position.z >= terrain.getZ() && (position.z <= terrain.getZ() + Terrain.SIZE)){
                    return terrain;
                }
            }
        }
        return null;
    }

}
