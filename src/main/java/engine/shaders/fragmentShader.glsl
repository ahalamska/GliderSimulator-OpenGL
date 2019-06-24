#version 150

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVec;
in vec3 toCameraVec;
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflection;
uniform vec3 skyColour;

void main(void){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVec = normalize(toLightVec);

    float nDot1 = dot(unitNormal, unitLightVec);
    float brightness = max(nDot1, 0.2);
    vec3 diffuse = brightness * lightColour;

    vec3 unitToCameraVec = normalize(toCameraVec);
    vec3 lightDirection = -unitLightVec;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitToCameraVec);
    specularFactor= max(specularFactor, 0.0);
    float dumpedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dumpedFactor * lightColour * reflection;

    out_Color = vec4(diffuse, 1f) * texture(textureSampler, pass_textureCoords) + vec4(finalSpecular, 1f);
    out_Color = mix(vec4(skyColour, 1.0), out_Color, visibility);
}
