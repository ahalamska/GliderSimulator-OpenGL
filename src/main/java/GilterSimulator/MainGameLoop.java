package GilterSimulator;

import Engine.Entitys.Camera;
import Engine.Entitys.Light;
import Engine.Entitys.Plane;
import Engine.Textures.TextureModel;
import Engine.renderEngine.DisplayManager;
import Engine.renderEngine.MultipleRenderer;
import Engine.renderEngine.VAOsLoader;
import Engine.terrains.Terrain;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {

        System.out.println(Sys.getTime()+"start");
        DisplayManager.createDisplay();
        System.out.println(Sys.getTime()+"created Display");
        VAOsLoader loader = new VAOsLoader();
        MultipleRenderer renderer = new MultipleRenderer();

        System.out.println(Sys.getTime()+"renderen accesible");

        ObjectsManager objectsManager = new ObjectsManager();
        objectsManager.createObjects();


        System.out.println(Sys.getTime()+"Plane created");

        Light sun = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0,0,loader,new TextureModel(10,0.1f, loader.loadTextureFromJPG("sand1")));
        Terrain terrain2 = new Terrain(1,0,loader,new TextureModel(10,0.01f, loader.loadTextureFromJPG("sand1")));

        List<Terrain> terrains = new ArrayList<>();
        terrains.add(terrain);
        terrains.add(terrain2);


        System.out.println(Sys.getTime()+"Terrain crested");


        Plane plane = new Plane();
        Camera camera = new Camera(plane);

        System.out.println(Sys.getTime()+"Camera created");


        while (!Display.isCloseRequested()) {
            camera.move();
            plane.move();
            objectsManager.processObjects(renderer);
            renderer.processEntity(plane);
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
