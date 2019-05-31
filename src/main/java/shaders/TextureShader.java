package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class TextureShader extends ShaderProgramImplementation {

    private static final String VERTEX_FILE = "./src/main/java/shaders/vertexShaderTexture.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/shaders/fragmentShaderTexture.glsl";
    private int locationTransformationMatrix;

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
    }
    public void loadTranformationMatrix(Matrix4f matrix){
        super.loadMatrix(locationTransformationMatrix, matrix);
    }
}

