package draylar.intotheomega.api;

import draylar.intotheomega.impl.StructureStartExtensions;
import net.minecraft.structure.StructureStart;

public class StructureStartCache {

    public static StructureStartExtensions get(StructureStart start) {
        return ((StructureStartExtensions) (Object) start);
    }
}
