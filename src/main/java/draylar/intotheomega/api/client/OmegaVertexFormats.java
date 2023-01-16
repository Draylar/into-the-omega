package draylar.intotheomega.api.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;

import static net.minecraft.client.render.VertexFormats.*;

public class OmegaVertexFormats {

    public static final VertexFormatElement FLOAT = new VertexFormatElement(0, VertexFormatElement.DataType.FLOAT, VertexFormatElement.Type.GENERIC, 1);

    public static final VertexFormat POSITION_TEXTURE_COLOR_LIGHT_PROGRESS = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder()
            .put("Position", POSITION_ELEMENT)
            .put("UV0", TEXTURE_0_ELEMENT)
            .put("Color", COLOR_ELEMENT)
            .put("UV2", LIGHT_ELEMENT)
            .put("Progress", FLOAT)
            .build());
}
