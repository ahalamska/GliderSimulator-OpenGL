package GilterSimulator.Birds;

import Engine.Entitys.Entity;
import Engine.Textures.TextureModel;
import Engine.models.ModelWithTexture;
import Engine.models.RawModel;
import Engine.renderEngine.MultipleRenderer;
import Engine.renderEngine.OBJLoader;
import Engine.renderEngine.VAOsLoader;
import lombok.Getter;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class BirdManager {

    private List<Bird> birds = new ArrayList<>();

    public void createEagles(VAOsLoader loader, int count) throws IOException {
        for (int i = 0; i < count ; i++) {
            createEagle(loader);
        }
    }

    private void createBird(ModelWithTexture birdModelWithTexture){
        Random random = new SecureRandom();
        float x = random.nextFloat() * 1000 - 500;
        float y = random.nextFloat() * 1000 - 500;
        float z = random.nextFloat() * 1000 - 500;
        Vector3f position = new Vector3f(x,y,z);
         Bird newBird = new Bird(birdModelWithTexture, position, random.nextFloat() * 180f, random.nextFloat() * 180f,
                random.nextFloat() * 180f, 0.2f);
        birds.add(newBird);
    }

    private void createEagle(VAOsLoader loader) throws IOException {
        RawModel birdModel = OBJLoader.loadObjModel("Eagle", loader);
        TextureModel birdTexture = new TextureModel(10, 0.2f, loader.loadTextureFromPNG("Eagle"));
        ModelWithTexture birdModelWithTexture = new ModelWithTexture(birdModel, birdTexture);
        createBird(birdModelWithTexture);
    }

    public void countPosition(){
        for( Bird bird: birds){
            bird.move();
        }
    }
    public void processBirds(MultipleRenderer renderer){
        for (Entity bird : birds) {
            renderer.processEntity(bird);
        }
    }
}
