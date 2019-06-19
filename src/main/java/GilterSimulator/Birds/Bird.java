package GilterSimulator.Birds;

import Engine.Entitys.Entity;
import Engine.models.ModelWithTexture;
<<<<<<< HEAD
import Engine.renderEngine.DisplayManager;
import org.lwjgl.util.vector.Vector3f;

public class Bird extends Entity {



    private float speed = 15;
=======
import org.lwjgl.util.vector.Vector3f;

import java.security.SecureRandom;
import java.util.Random;

public class Bird extends Entity {

    private Vector3f destination = new Vector3f();

    private float speed = 1;
>>>>>>> b71ad6fb77337c3c87854a564a722cd72f7e5e44

    public Bird(ModelWithTexture model, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
<<<<<<< HEAD
    }

    public void move(){
        super.increaseRotation(0.001f,0.001f * DisplayManager.getFrameTimeSec(),0);
        float distance = speed * DisplayManager.getFrameTimeSec();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        float dy = (float) (distance * Math.sin(Math.toRadians(super.getRotX())));
        super.increasePosition(dx, dy, dz);

    }

=======
        getNewDestination();
    }

    public void move(){
        if(destination.equals(getPosition())) getNewDestination();
        float newX = getPosition().x;
        float newY= getPosition().y;
        float newZ= getPosition().z;
        if(getPosition().x - destination.x > 0){
            newX = getPosition().x - speed;
        }
        else if(getPosition().x - destination.x < 0){
            newX = getPosition().x + speed;
        }
        if(getPosition().y - destination.y > 0){
            newY = getPosition().y - speed;
        }
        else if(getPosition().y - destination.y < 0){
            newY = getPosition().y + speed;
        }
        if(getPosition().z - destination.z > 0){
            newZ = getPosition().z - speed;
        }
        else if(getPosition().z - destination.z < 0){
            newZ = getPosition().z + speed;
        }

        super.increasePosition(newX, newY, newZ);
    }

    private void getNewDestination() {
        Random random = new SecureRandom();
        speed = random.nextFloat()*10;
        destination.x = random.nextFloat() * 1000 - 50;
        destination.y = random.nextFloat() * 1000 - 50;
        destination.z = random.nextFloat() * 1000 - 50;

    }
>>>>>>> b71ad6fb77337c3c87854a564a722cd72f7e5e44

}
