package draylar.intotheomega.registry.client;

import draylar.intotheomega.api.client.OmegaVertexFormats;
import draylar.intotheomega.api.shader.OmegaCoreShader;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;

public class OmegaShaders {

    public static final OmegaCoreShader ENTITY_TRANSLUCENT_FOGLESS_SHADER = OmegaCoreShader.register("rendertype_entity_translucent_fogless", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
    public static final OmegaCoreShader ENTITY_TRANSLUCENT_TRUE_TRANSLUCENT_SHADER = OmegaCoreShader.register("rendertype_entity_true_translucent", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
    public static final OmegaCoreShader MATRIX_DUNGEON_FLOOR = OmegaCoreShader.register("matrix_dungeon_floor", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
    public static final OmegaCoreShader INDICATOR_PARTICLE = OmegaCoreShader.register("indicator_particle", OmegaVertexFormats.POSITION_TEXTURE_COLOR_LIGHT_PROGRESS);

    public static void initialize() {

    }
}
