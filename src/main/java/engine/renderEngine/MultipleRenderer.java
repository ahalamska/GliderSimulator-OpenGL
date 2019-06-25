package engine.renderEngine;

import engine.entitys.Camera;
import engine.entitys.Entity;
import engine.entitys.Light;
import engine.models.ModelWithTexture;
import engine.shaders.StaticShader;
import engine.shaders.TerrainShader;
import engine.terrains.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleRenderer {
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1F;
    private static final float FAR_PLANE = SkyBoxRenderer.SIZE*5f;
    private static final float RED = 0.4667f;
    private static final float GREEN = 0.5333f;
    private static final float BLUE = 0.6f;


    private StaticShader shader = new StaticShader();
    private EntityRenderer entityRenderer;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Map<ModelWithTexture, List<Entity>> entities = new HashMap<>();

    private Matrix4f projectionMatrix;
    private List<Terrain> terrains = new ArrayList<>();

    private SkyBoxRenderer skyBoxRenderer;

    public MultipleRenderer(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        skyBoxRenderer = new SkyBoxRenderer(VAOsLoader.getInstance(), projectionMatrix);
    }

    public void render(List<Light> lights, Camera camera){
        prepare();
        shader.start();
        shader.loadSkyColour(RED, GREEN, BLUE);
        shader.loadLights(lights);
        shader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        shader.stop();
        terrainShader.start();
        terrainShader.loadSkyColour(RED, GREEN, BLUE);
        terrainShader.loadLights(lights);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        skyBoxRenderer.render(camera, RED, GREEN, BLUE);
        terrains.clear();
        entities.clear();
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

public void processEntity(Entity entity){
    ModelWithTexture entityModel = entity.getModel();
    List<Entity> batch = entities.get(entityModel);
    if(batch!=null){
        batch.add(entity);
    }
    else{
        List<Entity> newBatch = new ArrayList<>();
        newBatch.add(entity);
        entities.put(entityModel, newBatch);
    }
}

public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

public void cleanUp(){
    shader.cleanUp();
    terrainShader.cleanUp();
}
public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
    }
}
