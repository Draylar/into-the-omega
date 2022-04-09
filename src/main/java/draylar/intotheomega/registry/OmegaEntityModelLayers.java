package draylar.intotheomega.registry;

import draylar.intotheomega.client.entity.model.MatriteEntityModel;
import draylar.intotheomega.client.entity.model.VoidWalkerModel;
import draylar.intotheomega.mixin.EntityModelLayersAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class OmegaEntityModelLayers {

    public static final EntityModelLayer VOID_WALKER = EntityModelLayersAccessor.registerMain("void_walker");
    public static final EntityModelLayer VOID_WALKER_INNER_ARMOR = EntityModelLayersAccessor.createInnerArmor("void_walker_inner_armor");
    public static final EntityModelLayer VOID_WALKER_OUTER_ARMOR = EntityModelLayersAccessor.createOuterArmor("void_walker_outer_armor");
    public static final EntityModelLayer MATRITE = EntityModelLayersAccessor.registerMain("matrite");

    public static void init() {
        EntityModelLayerRegistry.registerModelLayer(VOID_WALKER, () -> TexturedModelData.of(VoidWalkerModel.getModelData(Dilation.NONE, 0.0f), 32, 64));
        EntityModelLayerRegistry.registerModelLayer(VOID_WALKER_INNER_ARMOR, () -> TexturedModelData.of(VoidWalkerModel.getModelData(Dilation.NONE, 0.0f), 32, 64));
        EntityModelLayerRegistry.registerModelLayer(VOID_WALKER_OUTER_ARMOR, () -> TexturedModelData.of(VoidWalkerModel.getModelData(Dilation.NONE, 0.0f), 32, 64));
        EntityModelLayerRegistry.registerModelLayer(MATRITE, MatriteEntityModel::createTexturedModelData);
    }
}
