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

<<<<<<< HEAD

        BirdManager birdManager = new BirdManager();
        birdManager.createEagles(loader, 500);


=======
        System.out.println(Sys.getTime()+"renderen accesible");
        BirdManager birdManager = new BirdManager();
        birdManager.createEagles(loader, 1);
>>>>>>> b71ad6fb77337c3c87854a564a722cd72f7e5e44

        System.out.println(Sys.getTime()+"Birds created");

        RawModel planeModel = OBJLoader.loadObjModel("Low_poly_UFO", loader);
        TextureModel planeTexture = new TextureModel(10, 2, loader.loadTextureFromPNG("ufo_diffuse"));
        ModelWithTexture planeModelWithTexture = new ModelWithTexture(planeModel, planeTexture);

        System.out.println(Sys.getTime()+"Plane created");

        Light sun = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0,0,loader,new TextureModel(10,0.1f, loader.loadTextureFromJPG("Sand1")));
        Terrain terrain2 = new Terrain(1,0,loader,new TextureModel(10,0.1f, loader.loadTextureFromJPG("Sand2")));

        List<Terrain> terrains = new ArrayList<>();
        terrains.add(terrain);
        terrains.add(terrain2);


        System.out.println(Sys.getTime()+"Terrain crested");

        Random random = new Random();
        Plane plane = new Plane(planeModelWithTexture, new Vector3f(random.nextInt(800), Plane.STARTING_ALTITUDE, random.nextInt(800)),
                0 , random.nextInt(360), 0, 0.3f);
        Camera camera = new Camera(plane);

        System.out.println(Sys.getTime()+"Camera created");


        while (!Display.isCloseRequested()) {
            birdManager.countPosition();
            camera.move();
            plane.move();
<<<<<<< HEAD
=======
            System.out.println(plane.getPosition().y);
            //birdManager.countPosition();
>>>>>>> b71ad6fb77337c3c87854a564a722cd72f7e5e44
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
