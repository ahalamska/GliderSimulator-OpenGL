package engine.shaders;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderProgramImplementation implements ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgramImplementation(String vertexFile, String fragmentFile) {
        try {
            vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
            fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }


    public int getUniformLocation(String uniformName){
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    public void loadFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }

    public void load3DVector(int location, Vector3f vector3f){
        GL20.glUniform3f(location, vector3f.x, vector3f.y, vector3f.z);
    }

    public void load2DVector(int location, Vector2f vector2f) {
        GL20.glUniform2f(location, vector2f.x, vector2f.y);
    }

    public void loadBoolean(int location, boolean value){
        float toLoad = 0;
        if(value){
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);

    }

    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    @Override
    public int loadShader(String file, int type) throws IOException {
        StringBuilder shaderContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderContent.append(line).append("//\n");
            }
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderContent);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

    @Override
    public void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    @Override
    public void start() {
        GL20.glUseProgram(programID);
    }

    @Override
    public void stop() {
        GL20.glUseProgram(0);
    }

    @Override
    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);

    }
}
