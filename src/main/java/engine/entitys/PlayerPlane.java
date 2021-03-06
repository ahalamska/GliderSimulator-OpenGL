package engine.entitys;

import GilterSimulator.ObjectsManager;
import GilterSimulator.TerrainManager;
import engine.Textures.TextureModel;
import engine.models.ModelWithTexture;
import engine.renderEngine.DisplayManager;
import engine.renderEngine.OBJLoader;
import engine.renderEngine.VAOsLoader;
import engine.terrains.Terrain;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

@Getter
@Setter
public class PlayerPlane extends Entity {

    private static PlayerPlane instance;

    public static final int STARTING_ALTITUDE = 2100;
    //used
    private static final float MAX_ROLL = 20f;
    private static final float ROLL_SPEED = 0.5f;
    private static final float MAX_TURN_SPEED = 20f;
    private static final float GRAVITY_DROP_PER_SECOND = -25.0f;
    private static final float MAX_PITCH = 200f;
    private static final float PITCH_SPEED = 0.2f;
    private static final float PITCH_COEFF = 0.55f;
    //not used
    private static final float WIND_SUPPRESS = 0.9f;
    private static final float NORMAL_SUPPRESS = 2f;
    private static final float FAST_SUPPRESS = 4f;
    private static final float MAX_SPEED = 200f;
    //altitude of the lowest vertex of the model with initializing plane y on 0: lowest_y_coordinate*scale
    private float currentSpeed = 100f;
    private float currentDownSpeed = 100f;
    private float currentTurnSpeed = 0;
    private boolean crashed = false;
    private boolean flying = true;
    @Getter
    private Terrain currentTerrain;



    public static PlayerPlane getInstance(){
        if(instance == null){
            try {
                instance = new PlayerPlane();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public PlayerPlane() throws IOException {

        super(new ModelWithTexture(OBJLoader.loadObjModel("plane",
                VAOsLoader.getInstance()), new TextureModel(10, 2, VAOsLoader
                .getInstance()
                .loadTexture("plane_textures", "JPG"))), new Vector3f(Terrain.SIZE/2, PlayerPlane.STARTING_ALTITUDE, Terrain.SIZE/2), 0, 0, 0, 0.3f);
        currentTerrain = TerrainManager.getInstance().getTerrains().get(0);
    }

    public void move() throws IOException {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSec(), 0);
        currentSpeed = Math.min(MAX_SPEED, (float)
                Math.max(currentSpeed * (1 + Math.sin(Math.toRadians(super.getRotX()))) , 10));
        float distance = currentSpeed * DisplayManager.getFrameTimeSec();
        float dx =
                (float) (distance * Math.sin(Math.toRadians(super.getRotY()) * Math.cos(Math.toRadians(super.getRotX()))));
        float dz =
                (float) (distance * Math.cos(Math.toRadians(super.getRotY()) * Math.cos(Math.toRadians(super.getRotX()))));
        float dy =
                (float) (GRAVITY_DROP_PER_SECOND + Math.sin(super.getRotX())*10 * PITCH_COEFF) * DisplayManager
                .getFrameTimeSec();
        super.increasePosition(dx, dy, dz);
        PlayerPlane.getInstance().setPosition(getPosition());
        crashed = PlayerPlane.getInstance().isCrashed();
        flying = PlayerPlane.getInstance().isFlying();
        Terrain lastTerrain = currentTerrain;
        currentTerrain = super.getCurrentTerrain(TerrainManager.getInstance().getTerrains());

        if ((currentTerrain == null)) {
            System.out.println("Out of the Map");
        }
        assert currentTerrain != null;
        float terrainHeight = currentTerrain.getHeightOfTerrain(super.getPosition().x , super.getPosition().z);
        if(super.getPosition().y < terrainHeight){
            flying = false;
            if(currentSpeed > 30f) {
                crashed = true;
            }
            super.getPosition().y = terrainHeight;
        }

        if (!lastTerrain.equals(currentTerrain)){
            ObjectsManager.getInstance().createObjects();
        }
    }


    public void checkInputs() {

        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            super.setRotX(getRotX() + 3);

        } else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            super.setRotX(getRotX() - 3);

        }
        else {
            if (super.getRotX() > 0) super.setRotX(Math.max(super.getRotX() - PITCH_SPEED, 0));
            else super.setRotX(Math.min(super.getRotX() + PITCH_SPEED, 0));
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            super.setRotZ(Math.min(super.getRotZ() + ROLL_SPEED, MAX_ROLL));


        } else if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            super.setRotZ(Math.max(super.getRotZ() - ROLL_SPEED, -MAX_ROLL));
        } else {
            if (super.getRotZ() > 0) super.setRotZ(Math.max(super.getRotZ() - ROLL_SPEED, 0));
            else super.setRotZ(Math.min(super.getRotZ() + ROLL_SPEED, 0));
        }
        currentTurnSpeed = -MAX_TURN_SPEED * super.getRotZ() / MAX_ROLL;

    }
    public boolean isFlying() {return flying;}

    public boolean isCrashed() {return crashed;}
}
