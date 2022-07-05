attribute vec2 vertexCoord;

varying vec2 vertexPosition;

void main() {
    vertexPosition = vertexCoord;
    gl_Position = vec4(vertexCoord.x, vertexCoord.y, 0, 1.0);
}