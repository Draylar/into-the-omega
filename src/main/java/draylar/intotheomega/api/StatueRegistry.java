package draylar.intotheomega.api;

import draylar.intotheomega.IntoTheOmegaClient;
import net.devtech.arrp.json.blockstate.JState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class StatueRegistry {

    private static final Map<Block, StatueData> SUPPLIERS = new HashMap<>();
    private static final List<StatueManipulator> LISTENERS = new ArrayList<>();

    public static void register(Block block, StatueData supplier) {
        Identifier id = Registry.BLOCK.getId(block);
        SUPPLIERS.put(block, supplier);
        LISTENERS.forEach(listener -> listener.run(block, supplier));
        IntoTheOmegaClient.RESOURCE_PACK.addBlockState(JState.state(JState.variant(JState.model("intotheomega:block/statue"))), id);
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public static StatueData get(Block block) {
        return SUPPLIERS.get(block);
    }

    public static void forEach(StatueManipulator manipulator) {
        SUPPLIERS.forEach(manipulator::run);
    }

    public static void addListener(StatueManipulator manipulator) {
        LISTENERS.add(manipulator);
    }

    @FunctionalInterface
    public interface ModelSupplier {
        EntityModel<?> create();
    }

    @FunctionalInterface
    public interface StatueManipulator {
        void run(Block block, StatueData data);
    }

    public static class StatueData {

        private final EntityType<?> type;
        private final ModelSupplier supplier;
        private float verticalOffset = 0;

        public StatueData(EntityType<?> type, ModelSupplier supplier) {
            this.type = type;
            this.supplier = supplier;
        }

        public StatueData(EntityType<?> type, ModelSupplier supplier, float verticalOffset) {
            this.type = type;
            this.supplier = supplier;
            this.verticalOffset = verticalOffset;
        }

        public static StatueData of(EntityType<?> type, ModelSupplier supplier) {
            return new StatueData(type, supplier);
        }

        public static StatueData of(EntityType<?> type, ModelSupplier supplier, float verticalOffset) {
            return new StatueData(type, supplier, verticalOffset);
        }

        public EntityType<?> getType() {
            return type;
        }

        public ModelSupplier getSupplier() {
            return supplier;
        }

        public float getVerticalOffset() {
            return verticalOffset;
        }
    }
}
