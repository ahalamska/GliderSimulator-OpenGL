package renderEngine.shaders;

public interface ShaderProgram {

    int loadShader(String file, int type);

    void bindAttributes();

    void start();

    void stop();

    void cleanUp();


}
