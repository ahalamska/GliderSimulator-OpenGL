#version 150

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVec[2];
in vec3 toCameraVec;
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour[2];
uniform vec3 attenuation[2];
uniform float shineDamper;
uniform float reflection;
uniform vec3 skyColour;

void main(void){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToCameraVec = normalize(toCameraVec);
    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int i = 0; i<2; i++){
        float distance = length(toLightVec[i]);
        float attFactor = attenuation[i].x + (attenuation[i].t * distance) + (attenuation[i].z * distance * distance);
        vec3 unitLightVec = normalize(toLightVec[i]);
        float nDot1 = dot(unitNormal, unitLightVec);
        float brightness = max(nDot1, 0.0);
        vec3 lightDirection = -unitLightVec;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitToCameraVec);
        specularFactor= max(specularFactor, 0.0);
        float dumpedFactor = pow(specularFactor, shineDamper);
        totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
        totalSpecular = totalSpecular + (dumpedFactor * lightColour[i] * reflection)/attFactor;
    }
    totalDiffuse = max(totalDiffuse, 0);
    out_Color = vec4(totalDiffuse, 1f) * texture(textureSampler, pass_textureCoords) + vec4(totalSpecular, 1f);
    out_Color = mix(vec4(skyColour, 1.0), out_Color, visibility);
}
