package GilterSimulator;

import engine.entitys.Camera;
import engine.entitys.Light;
import engine.entitys.PlayerPlane;
import engine.Textures.TextureModel;
import engine.renderEngine.DisplayManager;
import engine.renderEngine.MultipleRenderer;
import engine.renderEngine.VAOsLoader;
import engine.terrains.Terrain;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {


        DisplayManager.createDisplay();

        MultipleRenderer renderer = new MultipleRenderer();


        Light sun = new Light(new Vector3f(1000, 2000, 1000), new Vector3f(1, 1, 1));

        TextureModel terrainModel = new TextureModel(10, 0.05f, VAOsLoader.getInstance().loadTexture("bottom1", "PNG"));
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                TerrainManager.getInstance().addTerrain(new Terrain(i, j, terrainModel, "heightMap1"));
            }
        }

        PlayerPlane plane = new PlayerPlane();

        Camera camera = new Camera(plane);


        while (!Display.isCloseRequested()) {
            camera.move();
            plane.move();
            ObjectsManager.getInstance().processObjects(renderer);
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
