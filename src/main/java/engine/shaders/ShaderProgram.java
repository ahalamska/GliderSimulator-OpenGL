package engine.shaders;

import java.io.IOException;

public interface ShaderProgram {

     int loadShader(String file, int type) throws IOException;

    void bindAttributes();

    void start();

    void stop();

    void cleanUp();

    void bindAttribute(int attribute, String variableName);

    int getUniformLocation(String uniformName);

    void getAllUniformLocations();




}
