package dev.ag2o.ez2d.backend.shader;

public class Shaders {
    public static final String vertex_shader = """
            
            #version 330 core
            layout (location = 0) in vec2 aPos;
            layout (location = 1) in vec2 aTexCoord;
            layout (location = 2) in vec4 aColor;
            
            out vec4 vColor;
            out vec2 vTexCoord;
            
            uniform mat4 uProjection;
            
            void main() {
                gl_Position = uProjection * vec4(aPos, 0.0, 1.0);
                vColor = aColor;
                vTexCoord = aTexCoord;
            }
            
            """;

    public static final String fragment_shader = """
            
            #version 330 core
            in vec4 vColor;
            in vec2 vTexCoord;
            
            out vec4 FragColor;
            
            uniform sampler2D uTexture;
            
            void main() {
                FragColor = texture(uTexture, vTexCoord) * vColor;
            }
            
            """;
}
