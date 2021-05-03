package draylar.intotheomega.entity.block;

import draylar.intotheomega.registry.OmegaBlockEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;

public class PhasePadBlockEntity extends BlockEntity {

    private boolean activated = false;
    private boolean highlight = false;

    public PhasePadBlockEntity() {
        super(OmegaBlockEntities.PHASE_PAD);
    }

    @Environment(EnvType.CLIENT)
    public void deactivate() {
        this.activated = false;
    }

    @Environment(EnvType.CLIENT)
    public void activate() {
        this.activated = true;
    }

    @Environment(EnvType.CLIENT)
    public void dehighlight() {
        this.highlight = false;
    }

    @Environment(EnvType.CLIENT)
    public void highlight() {
        if(activated) {
            this.highlight = true;
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean isActivated() {
        return activated;
    }

    @Environment(EnvType.CLIENT)
    public boolean isHighlight() {
        return highlight;
    }
}
