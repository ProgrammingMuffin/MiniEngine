uniform vec3 colors;

void main() {
    gl_FragColor = vec4(colors.x, colors.y, colors.z, 1.0);
}