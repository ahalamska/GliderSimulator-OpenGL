#version 150

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVec[2];
out vec3 toCameraVec;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[2];

const float density = 0.001;
const float gradient = 1.2;


void main(void){
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCam;
    pass_textureCoords = textureCoords * 40;

    surfaceNormal = (transformationMatrix* vec4(normal, 0f)).xyz;
    for(int i = 0 ; i<2 ; i++){
        toLightVec[i] = lightPosition[i] - worldPosition.xyz;
    }
    toCameraVec = (inverse(viewMatrix) * vec4(0f,0f,0f,1f)).xyz - worldPosition.xyz;

    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance*density), gradient));
    visibility = clamp(visibility, 0f, 1f);
}
