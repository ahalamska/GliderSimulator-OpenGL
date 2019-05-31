package engineTester;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import renderEngine.*;
import shaders.ShaderProgram;
import shaders.StaticShader;

public class MainGameLoop {

    public  static void main(String[] args){

        try {
            DisplayManager.createDisplay();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        VAOsLoader loader = new VAOsLoader();
        Renderer renderer = new Renderer();
        ShaderProgram shader = new StaticShader();

        float[] vertices = {
                -0.5f,0.5f,0,   //V0
                -0.5f,-0.5f,0,  //V1
                0.5f,-0.5f,0,   //V2
                0.5f,0.5f,0     //V3
        };

        int[] indices = {
                0,1,3,  //Top left triangle (V0,V1,V3)
                3,1,2   //Bottom right triangle (V3,V1,V2)
        };

        Model model = loader.loadToVAO(vertices,indices);

        while(!Display.isCloseRequested()){
            renderer.clean();
            shader.start();
            renderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
