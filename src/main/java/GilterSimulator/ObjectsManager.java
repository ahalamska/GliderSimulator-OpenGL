package GilterSimulator;

import Engine.Entitys.Entity;
import Engine.Textures.TextureModel;
import Engine.models.ModelWithTexture;
import Engine.models.RawModel;
import Engine.renderEngine.MultipleRenderer;
import Engine.renderEngine.OBJLoader;
import Engine.renderEngine.VAOsLoader;
import Engine.terrains.Terrain;
import GilterSimulator.Birds.BirdManager;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectsManager {

    private  BirdManager birdManager;
    private List<Entity> objects = new ArrayList<>();


    public void createObjects() throws IOException {
        //birdManager= new BirdManager();
        //birdManager.createEagles(5000);
        createTrees(200);


    }


    public void createTrees(int count) throws IOException {

        RawModel model = OBJLoader.loadObjModel("grass", VAOsLoader.getInstance());
        TextureModel textureModel = new TextureModel(10, 0.2f, VAOsLoader.getInstance().loadTexture("trawa1", "JPG"));
        ModelWithTexture modelWithTexture = new ModelWithTexture(model, textureModel);

        RawModel model2 = OBJLoader.loadObjModel("pien", VAOsLoader.getInstance());
        TextureModel textureModel2 = new TextureModel(10, 0.2f, VAOsLoader.getInstance().loadTexture("pien", "JPG"));
        ModelWithTexture modelWithTexture2 = new ModelWithTexture(model2, textureModel2);

        for (int i = 0; i < count ; i++) {
            createTree(modelWithTexture, 0.7f);
            createTree(modelWithTexture2, 0.1f);
        }
    }

    private void createTree(ModelWithTexture modelWithTexture, float scale){
        Random random = new SecureRandom();
        Terrain terrain = TerrainManager.getInstance().getTerrains().get(
                random.nextInt(TerrainManager.getInstance().getTerrains().size()-1));

        float x = (random.nextInt((int) Terrain.SIZE) + terrain.getX());
        float z = (random.nextInt((int) Terrain.SIZE) + terrain.getZ());
        float y = terrain.getHeightOfTerrain(x,z);
        Vector3f position = new Vector3f(x,y ,z);

        Entity entity = new Entity(modelWithTexture,position, 0, 0,
                0, scale );
        objects.add(entity);
    }

    public void processObjects(MultipleRenderer renderer){
        //birdManager.countPosition();
        //birdManager.processBirds(renderer);
        for (Entity object : objects){
            renderer.processEntity(object);
        }

    }


}
