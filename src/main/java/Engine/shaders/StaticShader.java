package Engine.shaders;

import Engine.Entitys.Camera;
import Engine.Entitys.Light;
import org.lwjgl.util.vector.Matrix4f;
import Engine.toolbox.Maths;

public class StaticShader extends ShaderProgramImplementation {

    private static final String VERTEX_FILE = "D:\\Kod\\OpenGL\\GliderSimulator\\src\\main\\java\\Engine\\shaders" +
            "\\vertexShader.glsl";
    private static final String FRAGMENT_FILE = "D:\\Kod\\OpenGL\\GliderSimulator\\src\\main\\java\\Engine\\shaders" +
            "\\fragmentShader.glsl";
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightColour;
    private int locationLightPosition;
    private int locationReflection;
    private int locationShineDamper;


    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {

        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2,"normal" );
    }

    @Override
    public void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationLightPosition = super.getUniformLocation("lightPosition");
        locationLightColour = super.getUniformLocation("lightColour");
        locationReflection = super.getUniformLocation("reflection");
        locationShineDamper = super.getUniformLocation("shineDamper");
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
    public void loadLight(Light light) {
        super.loadVector(locationLightPosition, light.getPosition());
        super.loadVector(locationLightColour, light.getColour());
    }
    public void loadShineVariables(float damper,  float reflection){
        super.loadFloat(locationReflection,reflection);
        super.loadFloat(locationShineDamper, damper);
    }
}

