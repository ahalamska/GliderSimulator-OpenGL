package engine.shaders;

import engine.entitys.Camera;
import engine.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SkyBoxShader extends ShaderProgramImplementation{

    private static final String VERTEX_FILE = "./src/main/java/engine/shaders/skyBoxVertexShader.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/engine/shaders/skyBoxFragmentShader.glsl";
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationFogColour;

    public SkyBoxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public void getAllUniformLocations() {
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationFogColour = super.getUniformLocation("fogColour");
    }

    public void loadFogColour(float r, float g, float b){
        super.load3DVector(locationFogColour, new Vector3f(r,g,b));
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        viewMatrix.m30 = 0;
        viewMatrix.m31 = 0;
        viewMatrix.m32 = 0;
        super.loadMatrix(locationViewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(locationProjectionMatrix, projection);
    }
}
