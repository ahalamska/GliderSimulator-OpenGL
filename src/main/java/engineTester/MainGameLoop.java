package engineTester;

import Entitys.Camera;
import Entitys.Entity;
import Entitys.Light;
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
import shaders.StaticShader;

import java.io.IOException;

public class MainGameLoop {

    public  static void main(String[] args){

        try {
            DisplayManager.createDisplay();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        VAOsLoader loader = new VAOsLoader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);


        ModelWithTexture modelWithTexture = null;
        try {
            RawModel rawModel = OBJLoader.loadObjModel("Low_poly_UFO", loader);
            TextureModel texture = new TextureModel(loader.loadTextureFromPNG("ufo_diffuse"));
            modelWithTexture = new ModelWithTexture(rawModel, texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Entity entity = new Entity(modelWithTexture, new Vector3f(0,0,-250),0,0,0,1);
        Light sun = new Light(new Vector3f(0,20,-20), new Vector3f(1,1,1));
        Camera camera = new Camera();
        while(!Display.isCloseRequested()){

            entity.increaseRotation(0.2f, 0.2f, 0.2f);
            camera.move();
            renderer.clean();
            shader.start();
            shader.loadLight(sun);
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
