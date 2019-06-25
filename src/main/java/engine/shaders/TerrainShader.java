package engine.shaders;

import engine.entitys.Camera;
import engine.entitys.Light;
import engine.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrainShader extends ShaderProgramImplementation {
    private static final int MAX_LIGHTS = 2;
    private static final String VERTEX_FILE = "./src/main/java/engine/shaders/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/engine/shaders/terrainFragmentShader.glsl";
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int[] locationLightPosition;
    private int[] locationLightColour;
    private int[] locationLightAttenuation;
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationSkyColour;


    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }

    @Override
    public void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationShineDamper = super.getUniformLocation("shineDamper");
        locationReflectivity = super.getUniformLocation("reflection");
        locationSkyColour = super.getUniformLocation("skyColour");
        locationLightPosition = new int[MAX_LIGHTS];
        locationLightColour = new int[MAX_LIGHTS];
        locationLightAttenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < 2 ; i++) {
            locationLightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            locationLightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            locationLightAttenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }



    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(locationShineDamper, damper);
        super.loadFloat(locationReflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadLights(List<Light> lights) {
        for (int i = 0; i<MAX_LIGHTS; i++) {
            if(i < lights.size()) {
                super.load3DVector(locationLightPosition[i], lights.get(i).getPosition());
                super.load3DVector(locationLightColour[i], lights.get(i).getColour());
                super.load3DVector(locationLightAttenuation[i], lights.get(i).getAttenuation());
            }
            else {
                super.load3DVector(locationLightPosition[i], new Vector3f(0,0,0));
                super.load3DVector(locationLightColour[i], new Vector3f(0,0,0));
                super.load3DVector(locationLightAttenuation[i], new Vector3f(1,0,0));
            }
        }
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(locationViewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(locationProjectionMatrix, projection);
    }

    public void loadSkyColour(float r, float g, float b){
        super.load3DVector(locationSkyColour, new Vector3f(r,g,b));
    }

}
