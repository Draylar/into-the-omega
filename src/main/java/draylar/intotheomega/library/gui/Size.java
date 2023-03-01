package draylar.intotheomega.library.gui;

import java.util.function.Function;

public abstract class Size {

    public static Size fixed(int pixels) {
        return new Fixed(pixels);
    }

    public static Size percentageWidth(double amount) {
        return new Percentage(parent -> (int) (parent.getWidth() * amount));
    }

    public static Size remainingWidth() {
        return new RemainingWidth();
    }

    public static Size percentageHeight(double amount) {
        return new Percentage(parent -> (int) (parent.getHeight() * amount));
    }

    public abstract int get(MenuElement<?> parent, MenuElement<?> child);

    private static class Fixed extends Size {

        private final int pixels;

        public Fixed(int pixels) {
            this.pixels = pixels;
        }

        @Override
        public int get(MenuElement<?> parent, MenuElement<?> child) {
            return pixels;
        }
    }

    private static class Percentage extends Size {

        private final Function<MenuElement<?>, Integer> base;

        public Percentage(Function<MenuElement<?>, Integer> base) {
            this.base = base;
        }

        @Override
        public int get(MenuElement<?> parent, MenuElement<?> child) {
            return base.apply(parent);
        }
    }

    private static class RemainingWidth extends Size {

        @Override
        public int get(MenuElement<?> parent, MenuElement<?> child) {
            return parent.getWidth() - child.getX();
        }
    }
}
