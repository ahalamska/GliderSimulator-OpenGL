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
    private List<Bird> planes = new ArrayList<>();




    public void createEagles(int count) throws IOException {
        RawModel birdModel = OBJLoader.loadObjModel("plane", VAOsLoader.getInstance());
        TextureModel birdTexture = new TextureModel(10, 0.2f, VAOsLoader.getInstance().loadTexture("plane", "jpg"));
        ModelWithTexture birdModelWithTexture = new ModelWithTexture(birdModel, birdTexture);
        for (int i = 0; i < count ; i++) {
            birds.add(createBird(birdModelWithTexture, 0.3f));
        }
    }

    public void createPlanes(int count) throws IOException {
        RawModel birdModel = OBJLoader.loadObjModel("Eagle", VAOsLoader.getInstance());
        TextureModel birdTexture = new TextureModel(10, 0.2f, VAOsLoader.getInstance().loadTexture("Eagle", "PNG"));
        ModelWithTexture birdModelWithTexture = new ModelWithTexture(birdModel, birdTexture);
        for (int i = 0; i < count ; i++) {
            planes.add(createBird(birdModelWithTexture, 1f));
        }
    }

    private Bird createBird(ModelWithTexture birdModelWithTexture, float scale){
        Random random = new SecureRandom();
        PlayerPlane.getInstance().getPosition();

        float x = (random.nextInt(2000) + PlayerPlane.getInstance().getPosition().x);
        float z = (random.nextInt(2000) + PlayerPlane.getInstance().getPosition().z);
        float y = PlayerPlane.getInstance().getPosition().y - 300 ;
        Vector3f position = new Vector3f(x,y ,z);
        return new Bird(birdModelWithTexture, position, random.nextFloat() * 180f, random.nextFloat() * 180f,
                random.nextFloat() * 180f, scale);
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

        List<Bird> toRemovePlanes = new ArrayList<>();
        for (Bird bird : planes) {
            bird.move();
            if (bird.isDead()) {
                toRemovePlanes.add(bird);
            }
        }
        for(Bird bird : toRemovePlanes){
            planes.remove(bird);
        }
    }

    public void processBirds(MultipleRenderer renderer){
        for (Entity bird : birds) {
            renderer.processEntity(bird);
        }
        for (Entity bird : planes) {
            renderer.processEntity(bird);
        }
    }
}
