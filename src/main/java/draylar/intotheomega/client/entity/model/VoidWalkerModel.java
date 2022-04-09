package draylar.intotheomega.client.entity.model;

import draylar.intotheomega.entity.VoidWalkerEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AbstractZombieModel;

public class VoidWalkerModel extends AbstractZombieModel<VoidWalkerEntity> {

    public VoidWalkerModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public boolean isAttacking(VoidWalkerEntity hostileEntity) {
        return false;
    }
}
