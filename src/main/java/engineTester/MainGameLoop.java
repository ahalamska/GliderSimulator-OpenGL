package engineTester;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

public class MainGameLoop {

    public  static void main(String[] args){

        try {
            DisplayManager.createDisplay();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        while(!Display.isCloseRequested()){
            DisplayManager.updateDisplay();
        }
        DisplayManager.closeDisplay();
    }
}
