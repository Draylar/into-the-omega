package draylar.intotheomega.library.gui;

import net.minecraft.text.Text;

public class MutableLabel implements Labeled {

    private Text value;

    public void setValue(Text value) {
        this.value = value;
    }

    @Override
    public Text getLabel() {
        return value;
    }
}
