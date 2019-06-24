package Engine.shaders;

import Engine.Entitys.Camera;
import Engine.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class SkyBoxShader extends ShaderProgramImplementation{

    private static final String VERTEX_FILE = "./src/main/java/Engine/shaders/skyBoxVertexShader.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/Engine/shaders/skyBoxFragmentShader.glsl";
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public SkyBoxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        viewMatrix.m30 = 0;
        viewMatrix.m31 = 0;
        viewMatrix.m32 = 0;
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
}
