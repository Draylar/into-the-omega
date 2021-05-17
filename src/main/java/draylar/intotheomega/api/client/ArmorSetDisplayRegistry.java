package draylar.intotheomega.api.client;

import draylar.intotheomega.item.api.SetArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ArmorSetDisplayRegistry {

    private static final Map<Class<? extends SetArmorItem>, SetDisplay> SET_DISPLAYS = new HashMap<>();

    public static void register(Class<? extends SetArmorItem> setClass, SetDisplay display) {
        SET_DISPLAYS.put(setClass, display);
    }

    @Nullable
    public static SetDisplay getDisplay(SetArmorItem item) {
        return SET_DISPLAYS.get(item.getClass());
    }

    public interface SetDisplay {
        void display(AbstractClientPlayerEntity player, ClientWorld world, float delta);
    }
}
