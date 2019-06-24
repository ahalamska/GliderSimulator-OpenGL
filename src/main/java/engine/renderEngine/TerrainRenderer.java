package engine.renderEngine;

import engine.Textures.TextureModel;
import engine.shaders.TerrainShader;
import engine.terrains.Terrain;
import engine.toolbox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrainRenderer {

    private TerrainShader terrainShader;

    public TerrainRenderer(TerrainShader terrainShader, Matrix4f projectionMatrix) {
        this.terrainShader = terrainShader;
        terrainShader.start();
        terrainShader.loadProjectionMatrix(projectionMatrix);
    }

    public void render(List<Terrain> terrains){
        for(Terrain terrain : terrains){
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }
    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ() ),
                0,0,0, 1);
        terrainShader.loadTransformationMatrix(transformationMatrix);
    }

    private void prepareTerrain(Terrain terrain) {
        GL30.glBindVertexArray(terrain.getModel().getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        TextureModel texture = terrain.getTexture();
        terrainShader.loadShineVariables(texture.getShineDamper(), texture.getReflection());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexture().getTextureID());
    }
}
