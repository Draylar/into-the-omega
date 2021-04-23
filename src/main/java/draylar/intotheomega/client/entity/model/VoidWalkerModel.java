package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.entity.VoidWalkerEntity;
import net.minecraft.client.render.entity.model.AbstractZombieModel;

public class VoidWalkerModel extends AbstractZombieModel<VoidWalkerEntity> {

    public VoidWalkerModel(float scale, boolean bl) {
        this(scale, 0.0F, 64, bl ? 32 : 64);
    }

    public VoidWalkerModel(float f, float g, int i, int j) {
        super(f, g, i, j);
    }

    @Override
    public boolean isAttacking(VoidWalkerEntity hostileEntity) {
        return false;
    }
}
