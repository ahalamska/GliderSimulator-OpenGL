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
    private float current_speed = 0;
    private float current_turn_speed = 0;

    private float run_speed = 20;
    private float turn_speed = 160;


/*    private static final float NORMAL_ACCELERATION = 20;
    private static final float FAST_ACCELERATION = 40;
    private static final float MAX_SPEED_FORWARD = 2000;
    private static final float MAX_SPEED_BACK = 20;

    private static final float WIND_SUPPRESS = 0.9f;
    private static final float NORMAL_SUPPRESS = 2f;
    private static final float FAST_SUPPRESS = 4f;*/

    public Plane(ModelWithTexture model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(){
        checkInputs();
        super.increaseRotation(0,current_turn_speed * DisplayManager.getFrameTimeSec(),0);
        float distance = current_speed * DisplayManager.getFrameTimeSec();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
    }

    public void checkInputs(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) ) {
            current_speed = run_speed;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            current_speed = -run_speed;
        }
        else current_speed = 0;

        if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            current_turn_speed = -turn_speed;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            current_turn_speed = turn_speed;
        }
        else current_turn_speed = 0;

    }
}
