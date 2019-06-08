package Engine.renderEngine;

import Engine.Entitys.Camera;
import Engine.Entitys.Entity;
import Engine.Entitys.Light;
import Engine.models.ModelWithTexture;
import Engine.shaders.StaticShader;
import Engine.shaders.TerrainShader;
import Engine.terrains.Terrain;
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
    private static final float FAR_PLANE = 1000;


    private StaticShader shader = new StaticShader();
    private EntityRenderer entityRenderer;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Map<ModelWithTexture, List<Entity>> entities = new HashMap<>();

    private Matrix4f projectionMatrix;
    private List<Terrain> terrains = new ArrayList<>();


    public MultipleRenderer(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);

    }

    public void render(Light sun, Camera camera){
    prepare();
    shader.start();
    shader.loadLight(sun);
    shader.loadViewMatrix(camera);
    entityRenderer.render(entities);
    shader.stop();
    terrainShader.start();
    terrainShader.loadLight(sun);
    terrainShader.loadViewMatrix(camera);
    terrainRenderer.render(terrains);
    terrainShader.stop();
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
        GL11.glClearColor(0.08f, 0.13f, 0.47f, 1);
    }
}
