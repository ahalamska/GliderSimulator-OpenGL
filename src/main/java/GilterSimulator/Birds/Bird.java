package GilterSimulator.Birds;

import Engine.Entitys.Entity;
import Engine.models.ModelWithTexture;
import Engine.renderEngine.DisplayManager;
import org.lwjgl.util.vector.Vector3f;

public class Bird extends Entity {



    private float speed = 15;

    public Bird(ModelWithTexture model, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(){
        super.increaseRotation(0.001f,0.001f * DisplayManager.getFrameTimeSec(),0);
        float distance = speed * DisplayManager.getFrameTimeSec();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        float dy = (float) (distance * Math.sin(Math.toRadians(super.getRotX())));
        super.increasePosition(dx, dy, dz);

    }


}
