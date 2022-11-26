uniform vec3 colors;

varying vec2 vertexPosition;

float fabs(float f) {
    if (f < 0.0) {
       return -f;
    }
    return f;
}

void main() {
//    gl_FragColor = vec4(colors.x, colors.y, colors.z, 1.0);
    gl_FragColor = vec4(colors.x * fabs(vertexPosition.x), colors.y * fabs(vertexPosition.y), colors.z * fabs((vertexPosition.x + vertexPosition.y)), 1.0);
}
