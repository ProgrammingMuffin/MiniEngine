#version 400
varying vec2 outTextureCoord;
uniform sampler2D tex;

void main() {
    gl_FragColor = texture(tex, outTextureCoord);
}