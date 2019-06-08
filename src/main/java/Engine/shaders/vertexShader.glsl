#version 150

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVec;
out vec3 toCameraVec;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;



void main(void){
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    pass_textureCoords = textureCoords;

    surfaceNormal = (transformationMatrix* vec4(normal, 0f)).xyz;
    toLightVec = lightPosition - worldPosition.xyz;
    toCameraVec = (inverse(viewMatrix) * vec4(0f,0f,0f,1f)).xyz - worldPosition.xyz;
}
