package shaders;

public class StaticShader extends ShaderProgramImplementation{

    private static final String VERTEX_FILE = "D:\\Kod\\OpenGL\\GliderSimulator\\src\\main\\java\\shaders\\vertexShader";
            private static final String FRAGMENT_FILE = "D:\\Kod\\OpenGL\\GliderSimulator\\src\\main\\java\\shaders" +
                    "\\fragmentShader";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
