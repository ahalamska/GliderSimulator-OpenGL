package engineTester;

import Textures.TextureModel;
import models.ModelWithTexture;
import models.RawModel;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Renderer;
import renderEngine.VAOsLoader;
import shaders.ShaderProgram;
import shaders.TextureShader;

import java.io.IOException;

public class MainGameLoop {

    public  static void main(String[] args){

        try {
            DisplayManager.createDisplay();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        VAOsLoader loader = new VAOsLoader();
        Renderer renderer = new Renderer();
        ShaderProgram shader = new TextureShader();

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
        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel rawModel = loader.loadToVAO(vertices,indices, textureCoords);
        ModelWithTexture modelWithTexture = null;
        try {
            TextureModel texture = new TextureModel(loader.loadTextureFromJPG("cup"));
            modelWithTexture = new ModelWithTexture(rawModel, texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!Display.isCloseRequested()){
            renderer.clean();
            shader.start();
            renderer.render(modelWithTexture);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
