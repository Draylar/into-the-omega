package draylar.intotheomega.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class IntoTheOmegaMixinConfigPlugin implements IMixinConfigPlugin {

    private static final String MIXIN_PACKAGE = "draylar.intotheomega.mixin";
    private static final boolean IS_CANVAS_LOADED = FabricLoader.getInstance().isModLoaded("canvas");

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if(IS_CANVAS_LOADED) {
            if(mixinClassName.endsWith("ItemRendererTransformationOverrideMixin")) {
                return false;
            }
        } else {
            if(mixinClassName.contains("canvas")) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
