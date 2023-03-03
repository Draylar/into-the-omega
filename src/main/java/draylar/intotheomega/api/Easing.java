package draylar.intotheomega.api;

public enum Easing {
    EASE_IN_CUBIC() {
        @Override
        public float apply(float value) {
            return value * value * value;
        }
    },
    EASE_OUT_5EXP() {
        @Override
        public float apply(float value) {
            value = Math.min(1.0f, value);
            return 1.0f - (float) Math.pow(value, 10);
        }
    };;

    public abstract float apply(float value);
}
