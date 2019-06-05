package shaders;

import Entitys.Camera;
import org.lwjgl.util.vector.Matrix4f;
import toolbox.Maths;

public class TextureShader extends ShaderProgramImplementation {

    private static final String VERTEX_FILE = "./src/main/java/shaders/vertexShaderTexture.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/shaders/fragmentShaderTexture.glsl";
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationColourMatrix;
    //private int locationColourMatrix;


    public TextureShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {

        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    public void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
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
}

