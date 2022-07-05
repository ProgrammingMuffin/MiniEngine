void main() {
    gl_FragColor = vec4(1.0, gl_FragCoord.x / 750.0, gl_FragCoord.y / 750.0, 1.0);
}