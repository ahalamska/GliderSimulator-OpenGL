package engine.terrains;

import engine.Textures.TextureModel;
import engine.models.RawModel;
import engine.renderEngine.VAOsLoader;
import engine.toolbox.Maths;
import lombok.Getter;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Getter
public class Terrain {

    public static final float SIZE = 20000;
    private static final int MAX_HEIGHT = 2000;
    private static final int MAX_PIXEL_COLOUR = 256 * 256 * 256;

    private float x;
    private float z;
    private RawModel model;
    private TextureModel texture;
    private  float[][] heights;


    public Terrain(int gridX, int gridZ, TextureModel texture, String heighMap) throws IOException {
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(heighMap);

    }

    public float getHeightOfTerrain(float worldX, float worldZ){
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
        float gridSqSize = SIZE / ((float) heights.length -1);
        int gridX =(int) Math.floor(terrainX / gridSqSize);
        int gridZ =(int) Math.floor(terrainZ / gridSqSize);
        if(gridX >= heights.length -1 || gridZ >= heights.length -1 || gridX <0 || gridZ <0){
            return 0;
        }
        float xCoord = (terrainX % gridSqSize)/gridSqSize;
        float zCoord = (terrainZ % gridSqSize)/gridSqSize;

        float answer;
        if (xCoord <= (1-zCoord)) {
            answer = Maths
                    .barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ], 0), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        } else {
            answer = Maths
                    .barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }
        return answer;



    }

    private RawModel generateTerrain(String heightMap) throws IOException {

        BufferedImage image = ImageIO.read(new File("terrain/" + heightMap + ".png"));

        int vertexCount = image.getHeight();
        heights = new float[vertexCount][vertexCount];
        int count = vertexCount * vertexCount;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (vertexCount - 1) * (vertexCount - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) vertexCount - 1) * SIZE;
                float height = getHeight(j, i, image);
                heights[j][i] = height;
                vertices[vertexPointer * 3 + 1] = getHeight(j, i, image);
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * SIZE;
                Vector3f normal = calculateNormal(j,i,image);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) j / ((float) vertexCount - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertexCount - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < vertexCount - 1; gz++) {
            for (int gx = 0; gx < vertexCount - 1; gx++) {
                int topLeft = (gz * vertexCount) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * vertexCount) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return VAOsLoader.getInstance().loadToVAO(vertices, indices, textureCoords, normals);
    }

    private Vector3f  calculateNormal( int x, int z, BufferedImage image){
        float heightL = getHeight(x-1, z, image);
        float heightR = getHeight(x+1, z, image);
        float heightD = getHeight(x, z-1, image);
        float heightU = getHeight(x, z+1, image);
        Vector3f normal = new Vector3f(heightL - heightR , 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }

    private float getHeight(int x, int z, BufferedImage image){
        if (x<0 || x>= image.getHeight() || z<0 || z>= image.getHeight()){
            return 0;
        }
        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOUR /2f;
        height /= MAX_PIXEL_COLOUR/2f;
        height*= MAX_HEIGHT;
        return height;
    }


}
