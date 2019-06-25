package GilterSimulator;

import GilterSimulator.Birds.Bird;
import GilterSimulator.Birds.BirdManager;
import engine.Textures.TextureModel;
import engine.entitys.Entity;
import engine.entitys.PlayerPlane;
import engine.models.ModelWithTexture;
import engine.models.RawModel;
import engine.renderEngine.MultipleRenderer;
import engine.renderEngine.OBJLoader;
import engine.renderEngine.VAOsLoader;
import engine.terrains.Terrain;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectsManager {

    private static ObjectsManager instance;

    private  BirdManager birdManager;
    private List<Entity> objects = new ArrayList<>();

    public static ObjectsManager getInstance(){
        if(instance == null){
            instance = new ObjectsManager();
        }
        return instance;
    }


    public void createObjects() throws IOException {
        birdManager= new BirdManager();
        birdManager.createEagles(1000);
        //createTrees(1);


    }


    public void createTrees(int count) throws IOException {

        RawModel model = OBJLoader.loadObjModel("stone", VAOsLoader.getInstance());
        TextureModel textureModel = new TextureModel(10, 0.2f, VAOsLoader.getInstance().loadTexture("sand2", "JPG"));
        ModelWithTexture modelWithTexture = new ModelWithTexture(model, textureModel);

        RawModel model2 = OBJLoader.loadObjModel("pien", VAOsLoader.getInstance());
        TextureModel textureModel2 = new TextureModel(10, 0.2f, VAOsLoader.getInstance().loadTexture("pien", "JPG"));
        ModelWithTexture modelWithTexture2 = new ModelWithTexture(model2, textureModel2);

        for (int i = 0; i < count ; i++) {
            createTree(modelWithTexture, 1f);
            createTree(modelWithTexture2, 0.05f);
        }
    }

    private void createTree(ModelWithTexture modelWithTexture, float scale){
        Random random = new SecureRandom();
        Terrain terrain = TerrainManager.getInstance().getTerrains().get(
                random.nextInt(TerrainManager.getInstance().getTerrains().size()-1));

        float x = (random.nextInt(10000) + PlayerPlane.getInstance().getPosition().x);
        float z = (random.nextInt(10000) + PlayerPlane.getInstance().getPosition().z);
        float y = terrain.getHeightOfTerrain(x,z);
        Vector3f position = new Vector3f(x,y ,z);

        Entity entity = new Entity(modelWithTexture,position, 0, 0,
                0, scale );
        objects.add(entity);
    }

    public void processObjects(MultipleRenderer renderer){
        birdManager.countPosition();
        birdManager.processBirds(renderer);
        for (Entity object : objects){
            renderer.processEntity(object);
        }
        float collisionDelta = 2.0f;
        for (Bird bird : birdManager.getBirds()) {
            if (Math.sqrt(Math.pow(bird.getPosition().x - PlayerPlane.getInstance().getPosition().x, 2) +
                    Math.pow(bird.getPosition().y - PlayerPlane.getInstance().getPosition().y, 2) +
                    Math.pow(bird.getPosition().z - PlayerPlane.getInstance().getPosition().z, 2)) < collisionDelta)
                        System.out.println("Collision with bird!!");            //TODO what to do when collision
        }
    }
}
