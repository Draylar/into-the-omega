package draylar.intotheomega.impl;

import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;

public interface StructurePieceExtensions {
    StructureStart getStructureStart();
    void setStructureStart(StructureStart start);

    public static StructurePieceExtensions get(StructurePiece piece) {
        return ((StructurePieceExtensions) piece);
    }
}
