package Entitys;

import lombok.Getter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

@Getter
public class Camera {

    private Vector3f position = new Vector3f();
    private float pitch;
    private float yaw;
    private float roll;

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) ){
            position.z-=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            position.x+=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            position.x-=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            position.z-=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=0.02f;
        }
    }
}
