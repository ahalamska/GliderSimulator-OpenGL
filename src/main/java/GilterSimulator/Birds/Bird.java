package GilterSimulator.Birds;

import Engine.Entitys.Entity;
import Engine.models.ModelWithTexture;
import org.lwjgl.util.vector.Vector3f;

import java.security.SecureRandom;
import java.util.Random;

public class Bird extends Entity {

    private Vector3f destination = new Vector3f();

    private float speed = 1;

    public Bird(ModelWithTexture model, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
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

}
