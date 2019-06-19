package Engine.Entitys;

import Engine.models.ModelWithTexture;
import Engine.renderEngine.DisplayManager;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

@Getter
@Setter
public class Plane extends Entity {

    //TODO real-physic plane movement
    private float current_speed = 30;
    private float current_turn_speed = 0;
    private float current_vertical_wind_speed = 0;

    //used
    private static final float MAX_ROLL = 20f;
    private static final float ROLL_SPEED = 0.3f;
    private static final float MAX_TURN_SPEED = 20f;
    private static final float GRAVITY_DROP_PER_SECOND = -0.6f;
    private static final float MAX_PITCH = 10f;
    private static final float PITCH_SPEED = 0.2f;
    private static final float PITCH_COEFF = 0.55f;

    public static final int STARTING_ALTITUDE = 20;
    //altitude of the lowest vertex of the modelwith initializing plane y on 0: lowest_y_coordinate*scale

    //not used
    private static final float WIND_SUPPRESS = 0.9f;
    private static final float NORMAL_SUPPRESS = 2f;
    private static final float FAST_SUPPRESS = 4f;

    public Plane(ModelWithTexture model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(){
        checkInputs();
        super.increaseRotation(0,current_turn_speed * DisplayManager.getFrameTimeSec(),0);
        float distance = current_speed * DisplayManager.getFrameTimeSec();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())*Math.cos(Math.toRadians(super.getRotX()))));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())*Math.cos(Math.toRadians(super.getRotX()))));
        float dy = (float) (GRAVITY_DROP_PER_SECOND+current_vertical_wind_speed+Math.sin(super.getRotX())*PITCH_COEFF)
                *DisplayManager.getFrameTimeSec();
        super.increasePosition(dx, dy, dz);
        detectGroundCollision();
    }

    public void checkInputs(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) ) {
            super.setRotX(Math.min(super.getRotX() + PITCH_SPEED, MAX_PITCH));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            super.setRotX(Math.max(super.getRotX() - PITCH_SPEED, -MAX_PITCH));
        }
        else {
            if (super.getRotX() > 0)
                super.setRotX(Math.max(super.getRotX() - PITCH_SPEED, 0));
            else
                super.setRotX(Math.min(super.getRotX() + PITCH_SPEED, 0));
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            super.setRotZ(Math.min(super.getRotZ()+ROLL_SPEED, MAX_ROLL));
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            super.setRotZ(Math.max(super.getRotZ()-ROLL_SPEED, -MAX_ROLL));
        }
        else {
            if(super.getRotZ()>0)
                super.setRotZ(Math.max(super.getRotZ()-ROLL_SPEED, 0));
            else
                super.setRotZ(Math.min(super.getRotZ()+ROLL_SPEED, 0));
        }
        current_turn_speed = -MAX_TURN_SPEED*super.getRotZ()/MAX_ROLL;
    }

    private void detectGroundCollision() {
        if (super.getPosition().y < 0)              //not exactly, check line 29
            System.out.println("Collision with groud!");
    }
}
