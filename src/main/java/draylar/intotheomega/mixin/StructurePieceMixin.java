package draylar.intotheomega.mixin;

import draylar.intotheomega.impl.StructurePieceExtensions;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StructurePiece.class)
public class StructurePieceMixin implements StructurePieceExtensions {

    private StructureStart start;

    @Override
    public StructureStart getStructureStart() {
        return start;
    }

    @Override
    public void setStructureStart(StructureStart start) {
        this.start = start;
    }
}
