package GilterSimulator;

import engine.Textures.TextureModel;
import engine.entitys.Camera;
import engine.entitys.Light;
import engine.entitys.PlayerPlane;
import engine.renderEngine.DisplayManager;
import engine.renderEngine.MultipleRenderer;
import engine.renderEngine.VAOsLoader;
import engine.terrains.Terrain;
import engine.text.FontType;
import engine.text.GUIText;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.IOException;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {


        DisplayManager.createDisplay();

        MultipleRenderer renderer = new MultipleRenderer();

        TextManager.getInstance().init(VAOsLoader.getInstance());

        FontType font = new FontType(VAOsLoader.getInstance().loadTexture("harrington", "PNG"), new File("./text/harrington.fnt"));


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
            if(plane.isFlying()) {
                plane.move();
            }
            ObjectsManager.getInstance().processObjects(renderer);
            renderer.processEntity(plane);
            TerrainManager.getInstance().processTerrains(renderer);
            renderer.render(sun, camera);
            if(!plane.isFlying()) {
                if (plane.isCrashed()) {
                    GUIText text = new GUIText("YOU DIED", 10, font, new Vector2f(0, 0.25f), 1f, true);
                    text.setColour(0.6f, 0, 0);
                } else {
                    GUIText text = new GUIText("YOU SUCCESFULLY LANDED", 5, font, new Vector2f(0, 0.3f), 1f, true);
                    text.setColour(0.8f, 1f, 0.8f);
                }
            }
            TextManager.getInstance().render();
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        TextManager.getInstance().cleanUp();
        VAOsLoader.getInstance().cleanUp();
        DisplayManager.closeDisplay();
    }
}
