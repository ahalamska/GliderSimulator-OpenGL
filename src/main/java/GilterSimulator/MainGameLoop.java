package GilterSimulator;

import Engine.Entitys.Camera;
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
import GilterSimulator.Birds.BirdManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {

        DisplayManager.createDisplay();
        VAOsLoader loader = new VAOsLoader();
        MultipleRenderer renderer = new MultipleRenderer();


        BirdManager birdManager = new BirdManager();
        birdManager.createEagles(loader, 500);



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
            birdManager.countPosition();
            camera.move();
            plane.move();
            renderer.processEntity(plane);
            birdManager.processBirds(renderer);
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
