package draylar.intotheomega.library.gui.state;

public class MutableState<T> {

    private T value;

    public MutableState(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
