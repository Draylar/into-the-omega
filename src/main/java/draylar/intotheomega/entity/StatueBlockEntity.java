package draylar.intotheomega.entity;

import draylar.intotheomega.api.StatueRegistry;
import draylar.intotheomega.registry.OmegaEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.entity.model.EntityModel;

public class StatueBlockEntity extends BlockEntity {

    @Environment(EnvType.CLIENT)
    private StatueRegistry.StatueData data = null;

    public StatueBlockEntity() {
        super(OmegaEntities.STATUE);
    }

    @Environment(EnvType.CLIENT)
    public StatueRegistry.StatueData getData() {
        if(data == null) {
            data = StatueRegistry.get(getCachedState().getBlock());
        }

        return data;
    }

    @Environment(EnvType.CLIENT)
    public EntityModel<?> createModel() {
        return getData().getSupplier().create();
    }
}
