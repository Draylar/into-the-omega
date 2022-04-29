package draylar.intotheomega.client.feature;

import draylar.intotheomega.client.entity.model.SectionedPlayerEntityModel;
import draylar.intotheomega.item.SkinArmorItem;
import draylar.intotheomega.registry.OmegaEntityModelLayers;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class SkinArmorFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, BipedEntityModel<AbstractClientPlayerEntity>> {

    private final SectionedPlayerEntityModel slimModel;
    private final SectionedPlayerEntityModel standardModel;

    public SkinArmorFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, BipedEntityModel<AbstractClientPlayerEntity>> context, EntityRendererFactory.Context factoryContext) {
        super(context);
        slimModel = new SectionedPlayerEntityModel(factoryContext.getPart(OmegaEntityModelLayers.SKIN_ARMOR_SLIM), true);
        standardModel = new SectionedPlayerEntityModel(factoryContext.getPart(OmegaEntityModelLayers.SKIN_ARMOR), false);
        slimModel.child = false;
        standardModel.child = false;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        SectionedPlayerEntityModel model = entity.getModel().equals("slim") ? slimModel : standardModel;
        model.animateModel(entity, limbAngle, limbDistance, tickDelta);
        model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        getContextModel().setAttributes(model);

        ItemStack head = entity.getEquippedStack(EquipmentSlot.HEAD);
        if(!head.isEmpty() && head.getItem() instanceof SkinArmorItem skinArmor) {
            model.setActive(SectionedPlayerEntityModel.Part.HEAD);
            model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(skinArmor.getTexture(entity, EquipmentSlot.HEAD, head))), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
        if(!chest.isEmpty() && chest.getItem() instanceof SkinArmorItem skinArmor) {
            model.setActive(SectionedPlayerEntityModel.Part.CHEST);
            model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(skinArmor.getTexture(entity, EquipmentSlot.CHEST, chest))), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        ItemStack legs = entity.getEquippedStack(EquipmentSlot.LEGS);
        if(!legs.isEmpty() && legs.getItem() instanceof SkinArmorItem skinArmor) {
            model.setActive(SectionedPlayerEntityModel.Part.LEGGINGS);
            model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(skinArmor.getTexture(entity, EquipmentSlot.LEGS, legs))), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        ItemStack feet = entity.getEquippedStack(EquipmentSlot.FEET);
        if(!feet.isEmpty() && feet.getItem() instanceof SkinArmorItem skinArmor) {
            model.setActive(SectionedPlayerEntityModel.Part.BOOTS);
            model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(skinArmor.getTexture(entity, EquipmentSlot.FEET, feet))), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }

        matrices.pop();
    }
}
