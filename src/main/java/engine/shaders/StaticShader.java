package engine.shaders;

import engine.entitys.Camera;
import engine.entitys.Light;
import org.lwjgl.util.vector.Matrix4f;
import engine.toolbox.Maths;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class StaticShader extends ShaderProgramImplementation {

    private static final int MAX_LIGHTS = 2;
    private static final String VERTEX_FILE = "./src/main/java/engine/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/engine/shaders/fragmentShader.glsl";
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int[] locationLightColour;
    private int[] locationLightPosition;
    private int[] locationLightAttenuation;
    private int locationReflection;
    private int locationShineDamper;
    private int locationSkyColour;


    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {

        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    public void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationReflection = super.getUniformLocation("reflection");
        locationShineDamper = super.getUniformLocation("shineDamper");
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

    public void loadTranformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(locationViewMatrix, viewMatrix);
    }

    public void loadLights(List<Light> lights) {
        for (int i = 0; i<MAX_LIGHTS; i++) {
            if(i < lights.size()) {
                super.loadVector(locationLightPosition[i], lights.get(i).getPosition());
                super.loadVector(locationLightColour[i], lights.get(i).getColour());
                super.loadVector(locationLightAttenuation[i], lights.get(i).getAttenuation());
            }
            else {
                super.loadVector(locationLightPosition[i], new Vector3f(0,0,0));
                super.loadVector(locationLightColour[i], new Vector3f(0,0,0));
                super.loadVector(locationLightAttenuation[i], new Vector3f(1,0,0));
                System.out.println(" ");
            }
        }
    }

    public void loadShineVariables(float damper, float reflection) {
        super.loadFloat(locationReflection, reflection);
        super.loadFloat(locationShineDamper, damper);
    }

    public void loadSkyColour(float r, float g, float b){
        super.loadVector(locationSkyColour, new Vector3f(r,g,b));
    }
}

