package shaders;

public class StaticShader extends ShaderProgramImplementation{

    private static final String VERTEX_FILE = "./src/main/java/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "./src/main/java/shaders/fragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public void getAllUniformLocations() {

    }
}
