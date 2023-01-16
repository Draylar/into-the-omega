package draylar.intotheomega.api.shader;

import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class OmegaCoreShader implements Supplier<Shader> {

    private static final Set<OmegaCoreShader> CORE_SHADERS = new HashSet<>();
    private final String id;
    private final VertexFormat format;
    private Shader shader;

    public OmegaCoreShader(String id, VertexFormat format) {

        this.id = id;
        this.format = format;
    }

    public static OmegaCoreShader register(String id, VertexFormat format) {
        OmegaCoreShader shader = new OmegaCoreShader(id, format);
        CORE_SHADERS.add(shader);
        return shader;
    }

    public static Set<OmegaCoreShader> getCoreShaders() {
        return CORE_SHADERS;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

    public String getId() {
        return id;
    }

    public VertexFormat getFormat() {
        return format;
    }

    public void setup(ResourceManager manager) throws IOException {
        try {
            this.shader = new Shader(manager, id, format);
        } catch (Exception any) {
            any.printStackTrace();
        }
    }

    @Override
    public Shader get() {
        return shader;
    }
}
