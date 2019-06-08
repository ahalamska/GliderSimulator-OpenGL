package Engine.Entitys;

import lombok.Getter;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

@Getter
public class Camera {

    private float distanceToPlayer = 100;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f();
    private float pitch = 20;
    private float yaw = 0;
    private float roll;
    private Plane plane;

    public Camera(Plane plane) {
        this.plane = plane;
    }

    private  void calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = plane.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = plane.getPosition().x - offsetX;
        position.z = plane.getPosition().z - offsetZ;
        position.y = plane.getPosition().y + verticalDistance;

    }

    private  float calculateHorizontalDistance(){
        return (float) (distanceToPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private  float calculateVerticalDistance(){
        return (float) (distanceToPlayer * Math.sin(Math.toRadians(pitch)));
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateangleAroundPlayer();

        calculateCameraPosition(calculateHorizontalDistance(), calculateVerticalDistance());
        this.yaw = 180 - (plane.getRotY() + angleAroundPlayer);
    }

    private  void calculateZoom(){
        float zoomLvl = Mouse.getDWheel() * 0.1f;
        distanceToPlayer -= zoomLvl;
    }
    private void calculatePitch(){
        if(Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateangleAroundPlayer(){
        if(Mouse.isButtonDown(1)){
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }
}
