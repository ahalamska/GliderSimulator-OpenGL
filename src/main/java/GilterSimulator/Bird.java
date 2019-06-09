package GilterSimulator;

import Engine.Entitys.Entity;
import Engine.Textures.TextureModel;
import Engine.models.ModelWithTexture;
import Engine.models.RawModel;
import Engine.renderEngine.OBJLoader;
import org.lwjgl.util.vector.Vector3f;

public class Bird extends Entity {

    public void createBird() {
        RawModel birdModel = OBJLoader.loadObjModel("Eagle", loader);
        TextureModel birdTexture = new TextureModel(10, 0.2f, loader.loadTextureFromPNG("Eagle"));
        ModelWithTexture birdModelWithTexture = new ModelWithTexture(birdModel, birdTexture);
        super(birdModelWithTexture, new Vector3f(x,y,z), random.nextFloat() * 90f,
                random.nextFloat() * 90f, 0, 0.1f));
    }


}
