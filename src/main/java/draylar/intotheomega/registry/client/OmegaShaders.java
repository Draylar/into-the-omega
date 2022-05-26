package draylar.intotheomega.registry.client;

import draylar.intotheomega.api.shader.OmegaCoreShader;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;

public class OmegaShaders {

    private static final OmegaCoreShader ENTITY_TRANSLUCENT_FOGLESS_SHADER = OmegaCoreShader.register("rendertype_entity_translucent_fogless", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
    protected static final RenderPhase.Shader ENTITY_TRANSLUCENT_FOGLESS_PHASE = new RenderPhase.Shader(ENTITY_TRANSLUCENT_FOGLESS_SHADER::getShader);

    public static void initialize() {

    }
}
