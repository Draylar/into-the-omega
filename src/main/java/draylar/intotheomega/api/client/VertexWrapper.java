package draylar.intotheomega.api.client;

import net.minecraft.client.render.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows users to write to a {@link VertexConsumer} with an arbitrary {@link VertexFormat} order.
 */
public class VertexWrapper implements VertexConsumer {

    private final VertexConsumer delegate;
    private final VertexFormat format;

    private final float[] vertex = new float[3];

    private final int[] color = new int[] {
            255,
            255,
            255,
            255
    };

    private final float[] texture = new float[] {
            0.0f,
            0.0f
    };

    private final float[] normal = new float[] {
            0.0f,
            1.0f,
            0.0f
    };

    private final int[] overlay = new int[] {
            OverlayTexture.DEFAULT_UV,
            OverlayTexture.DEFAULT_UV
    };

    private final int[] light = new int[] {
            LightmapTextureManager.MAX_LIGHT_COORDINATE,
            LightmapTextureManager.MAX_LIGHT_COORDINATE
    };

    private Map<VertexFormatElement, Float> global = null;

    private VertexWrapper(VertexConsumer delegate, VertexFormat format) {
        this.delegate = delegate;
        this.format = format;
    }

    public static VertexWrapper wrap(VertexConsumer consumer, VertexFormat format) {
        return new VertexWrapper(consumer, format);
    }

    @Override
    public VertexConsumer vertex(double x, double y, double z) {
        vertex[0] = (float) x;
        vertex[1] = (float) y;
        vertex[2] = (float) z;
        return this;
    }

    @Override
    public VertexConsumer color(int red, int green, int blue, int alpha) {
        color[0] = red;
        color[1] = green;
        color[2] = blue;
        color[3] = alpha;
        return this;
    }

    @Override
    public VertexConsumer texture(float u, float v) {
        texture[0] = u;
        texture[1] = v;
        return this;
    }

    @Override
    public VertexConsumer overlay(int u, int v) {
        overlay[0] = u;
        overlay[1] = v;
        return this;
    }

    @Override
    public VertexConsumer light(int u, int v) {
        light[0] = u;
        light[1] = v;
        return this;
    }

    @Override
    public VertexConsumer normal(float x, float y, float z) {
        normal[0] = x;
        normal[1] = y;
        normal[2] = z;
        return this;
    }

    @Override
    public void next() {
        for (VertexFormatElement element : format.getElements()) {
            if(element == VertexFormats.COLOR_ELEMENT) {
                delegate.color(color[0], color[1], color[2], color[3]);
            } else if(element == VertexFormats.POSITION_ELEMENT) {
                delegate.vertex(vertex[0], vertex[1], vertex[2]);
            } else if(element == VertexFormats.LIGHT_ELEMENT) {
                delegate.light(light[0], light[1]);
            } else if(element == VertexFormats.NORMAL_ELEMENT) {
                delegate.normal(normal[0], normal[1], normal[2]);
            } else if(element == VertexFormats.OVERLAY_ELEMENT) {
                delegate.overlay(overlay[0], overlay[1]);
            } else if(element == VertexFormats.TEXTURE_ELEMENT) {
                delegate.texture(texture[0], texture[1]);
            } else {
                if(delegate instanceof BufferVertexConsumer consumer && global != null) {
                    Float global = this.global.get(element);
                    if(global != null) {
                        consumer.putFloat(0, global);
                        consumer.nextElement();
                    }
                }
            }
        }

        delegate.next();
    }

    @Override
    public void fixedColor(int red, int green, int blue, int alpha) {

    }

    @Override
    public void unfixColor() {

    }

    public void global(VertexFormatElement element, float value) {
        if(global == null) {
            global = new HashMap<>();
        }

        global.put(element, value);
    }
}
