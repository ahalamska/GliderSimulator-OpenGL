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

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {

        System.out.println(Sys.getTime() + "start");
        DisplayManager.createDisplay();
        System.out.println(Sys.getTime() + "created Display");
        MultipleRenderer renderer = new MultipleRenderer();

        System.out.println(Sys.getTime() + "renderen accesible");

        Light sun = new Light(new Vector3f(1000, 2000, 1000), new Vector3f(1, 1, 1));

        TextureModel terrainModel = new TextureModel(10, 0.05f, VAOsLoader.getInstance().loadTextureFromJPG("sand1"));
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                TerrainManager.getInstance().addTerrain(new Terrain(i, j, terrainModel, "heightMap"));
            }}

        System.out.println(Sys.getTime()+"Terrain crested");

        ObjectsManager objectsManager = new ObjectsManager();
        objectsManager.createObjects();



        Plane plane = new Plane();
        System.out.println(Sys.getTime() + "Plane created");

        Camera camera = new Camera(plane);

        System.out.println(Sys.getTime()+"Camera created");


        while (!Display.isCloseRequested()) {
            camera.move();
            plane.move();
            objectsManager.processObjects(renderer);
            renderer.processEntity(plane);
            TerrainManager.getInstance().processTerrains(renderer);
            renderer.render(sun, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        VAOsLoader.getInstance().cleanUp();
        DisplayManager.closeDisplay();
    }
}
