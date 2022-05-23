package draylar.intotheomega.api;

import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;

import java.util.ArrayDeque;

public class BlockTransformationMatrix {

    private final ArrayDeque<Element> elements = new ArrayDeque<>();


    public BlockTransformationMatrix() {
        elements.add(new Element());
    }

    public void translate(double x, double y, double z) {
        elements.getLast().position().multiplyByTranslation((float) x, (float) y, (float) z);
    }

    public void translate(float x, float y, float z) {
        elements.getLast().position().multiplyByTranslation(x, y, z);
    }

    public void multiply(Quaternion quaternion) {
        Element topElement = elements.getLast();
        topElement.position.multiply(quaternion);
//        topElement.normal.multiply(quaternion);
    }

    public void scale(float xScale, float yScale, float zScale) {
//        float multiplier = (float) Math.cbrt((1f / xScale) * (1f / yScale) * (1f / zScale));

        Element topElement = elements.getLast();
        topElement.position.multiply(Matrix4f.scale(xScale, yScale, zScale));
//        topElement.normal.multiply(Matrix3f.scale(multiplier * (1 / xScale), multiplier * (1 / yScale), multiplier * (1 / zScale)));
    }

    public void push() {
        elements.add(new Element(elements.getLast()));
    }

    public Element pop() {
        return elements.removeLast();
    }

    public Element peek() {
        return elements.peekLast();
    }

    public static class Element {

        private final Matrix4f position;
        private final Matrix3f normal;

        public Element() {
            position = new Matrix4f();
            position.loadIdentity();
            normal = new Matrix3f();
            normal.loadIdentity();
        }

        public Element(Element previousElement) {
            this.position = previousElement.position.copy();
            this.normal = previousElement.normal.copy();
        }

        public Matrix4f position() {
            return position;
        }
    }
}
