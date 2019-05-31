package engineTester;

import Entitys.Camera;
import Entitys.Entity;
import Textures.TextureModel;
import models.ModelWithTexture;
import models.RawModel;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import renderEngine.VAOsLoader;
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
        TextureShader shader = new TextureShader();
        Renderer renderer = new Renderer(shader);

        RawModel rawModel = OBJLoader.loadObjModel("Bird", loader);
        ModelWithTexture modelWithTexture = null;
        try {
            TextureModel texture = new TextureModel(loader.loadTextureFromJPG("cup"));
            modelWithTexture = new ModelWithTexture(rawModel, texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Entity entity = new Entity(modelWithTexture, new Vector3f(-1,0,0),0,0,0,1);

        Camera camera = new Camera();
        while(!Display.isCloseRequested()){
            entity.increasePosition(0.002f, 0, -0.05f);
            entity.increaseRotation(0.1f, 0.1f, 0.1f);
            camera.move();
            renderer.clean();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
