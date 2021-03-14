package draylar.intotheomega.entity.block;

import draylar.intotheomega.registry.OmegaEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

// Starts out as a smaller pillar that is "glitch" vibrating and pulsating colors
// Interacting with the pillar throws all nearby entities back, then summons up a giant tower from the bottom of the end
public class EternalPillarBlockEntity extends BlockEntity {

    public EternalPillarBlockEntity() {
        super(OmegaEntities.ETERNAL_PILLAR);
    }
}
