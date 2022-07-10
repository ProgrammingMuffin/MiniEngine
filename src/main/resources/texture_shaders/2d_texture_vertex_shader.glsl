attribute vec2 vertexCoord;
attribute vec2 textureCoord;

varying vec2 outTextureCoord;

void main() {
    outTextureCoord = textureCoord;
    gl_Position = vec4(vertexCoord.x, vertexCoord.y, 0, 1.0);
}