package GilterSimulator.Birds;

import engine.entitys.Entity;
import engine.entitys.PlayerPlane;
import engine.Textures.TextureModel;
import engine.models.ModelWithTexture;
import engine.models.RawModel;
import engine.renderEngine.MultipleRenderer;
import engine.renderEngine.OBJLoader;
import engine.renderEngine.VAOsLoader;
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

    public void createEagles(int count) throws IOException {
        RawModel birdModel = OBJLoader.loadObjModel("Eagle", VAOsLoader.getInstance());
        TextureModel birdTexture = new TextureModel(10, 0.2f, VAOsLoader.getInstance().loadTexture("Eagle", "PNG"));
        ModelWithTexture birdModelWithTexture = new ModelWithTexture(birdModel, birdTexture);
        for (int i = 0; i < count ; i++) {
            createBird(birdModelWithTexture);
        }
    }

    private void createBird(ModelWithTexture birdModelWithTexture){
        Random random = new SecureRandom();
        PlayerPlane.getInstance().getPosition();

        float x = (random.nextInt(3000) + PlayerPlane.getInstance().getPosition().x);
        float z = (random.nextInt(3000) + PlayerPlane.getInstance().getPosition().z);
        float y = (random.nextInt(100) + PlayerPlane.getInstance().getPosition().y);
        Vector3f position = new Vector3f(x,y ,z);
         Bird newBird = new Bird(birdModelWithTexture, position, random.nextFloat() * 180f, random.nextFloat() * 180f,
                random.nextFloat() * 180f, 0.6f);
        birds.add(newBird);
    }


    public void countPosition() {
        List<Bird> toRemove = new ArrayList<>();
        for (Bird bird : birds) {
            bird.move();
            if (bird.isDead()) {
                toRemove.add(bird);
            }
        }
        for(Bird bird : toRemove){
            birds.remove(bird);
        }
    }

    public void processBirds(MultipleRenderer renderer){
        for (Entity bird : birds) {
            renderer.processEntity(bird);
        }
    }
}
