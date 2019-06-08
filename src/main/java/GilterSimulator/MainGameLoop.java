package GilterSimulator;

import Engine.Entitys.Camera;
import Engine.Entitys.Entity;
import Engine.Entitys.Light;
import Engine.Entitys.Plane;
import Engine.Textures.TextureModel;
import Engine.models.ModelWithTexture;
import Engine.models.RawModel;
import Engine.renderEngine.DisplayManager;
import Engine.renderEngine.MultipleRenderer;
import Engine.renderEngine.OBJLoader;
import Engine.renderEngine.VAOsLoader;
import Engine.terrains.Terrain;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {

        DisplayManager.createDisplay();
        VAOsLoader loader = new VAOsLoader();

        MultipleRenderer renderer = new MultipleRenderer();
        RawModel birdModel = OBJLoader.loadObjModel("Eagle", loader);
        TextureModel birdTexture = new TextureModel(10, 0.2f, loader.loadTextureFromPNG("Eagle"));
        ModelWithTexture birdModelWithTexture = new ModelWithTexture(birdModel, birdTexture);
        List<Entity> birds = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            float x = random.nextFloat() * 1000 - 50;
            float y = random.nextFloat() * 1000 - 50;
            float z = random.nextFloat() * 1000 - 50;
            birds.add(new Entity(birdModelWithTexture, new Vector3f(x,y,z), random.nextFloat() * 90f,
                    random.nextFloat() * 90f, 0, 0.1f));
        }

        RawModel planeModel = OBJLoader.loadObjModel("plane", loader);
        TextureModel planeTexture = new TextureModel(10, 2, loader.loadTextureFromJPG("plane_textures"));
        ModelWithTexture planeModelWithTexture = new ModelWithTexture(planeModel, planeTexture);

        Light sun = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0,0,loader,new TextureModel(10,0.1f, loader.loadTextureFromJPG("Sand1")));
        Terrain terrain2 = new Terrain(1,0,loader,new TextureModel(10,0.1f,
                loader.loadTextureFromJPG("Sand2")));

        List<Terrain> terrains = new ArrayList<>();
        terrains.add(terrain);
        terrains.add(terrain2);




        Plane plane = new Plane(planeModelWithTexture, new Vector3f(0, 0, -10), 0  , 0, 0, 1.5f);
        Camera camera = new Camera(plane);


        while (!Display.isCloseRequested()) {
            camera.move();
            plane.move();
            System.out.println(plane.getRotY());
            renderer.processEntity(plane);
            for (Entity bird : birds) {
                renderer.processEntity(bird);
            }
            for (Terrain t : terrains){
                renderer.processTerrain(t);
            }
            renderer.render(sun, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
