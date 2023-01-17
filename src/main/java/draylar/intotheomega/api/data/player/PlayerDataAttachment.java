package draylar.intotheomega.api.data.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public record PlayerDataAttachment<T>(String id, T defaultValue, Bounds<T> range, SyncStrategy syncStrategy,
                                      RetentionStrategy retentionStrategy, Serializer<T> serializer) {

    private static final Map<String, PlayerDataAttachment<?>> REGISTRY = new HashMap<>();

    public static final Serializer<Integer> INTEGER_SERIALIZER = new IntegerSerializer();

    public static <T> PlayerDataAttachment<T> track(String id, T defaultValue, Bounds<T> range, SyncStrategy syncStrategy, RetentionStrategy retentionStrategy, Serializer<T> serializer) {
        PlayerDataAttachment<T> attachment = new PlayerDataAttachment<>(
                id,
                defaultValue,
                range,
                syncStrategy,
                retentionStrategy,
                serializer
        );

        REGISTRY.put(id, attachment);
        return attachment;
    }

    public static Map<String, PlayerDataAttachment<?>> getAll() {
        return REGISTRY;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void read(PlayerEntity target, NbtCompound root) {
        for (var entry : PlayerDataAttachment.getAll().entrySet()) {
            PlayerDataAttachment data = entry.getValue();
            String id = entry.getKey();
            if(root.contains(id)) {
                Object value = data.serializer().read(id, root, data.defaultValue());
                target.setPlayerData(data, value);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static NbtCompound write(PlayerEntity player, Map<PlayerDataAttachment<?>, Object> values) {
        NbtCompound itoData = new NbtCompound();
        for (var entry : values.entrySet()) {
            PlayerDataAttachment key = entry.getKey();
            Object value = entry.getValue();

            // Values should be accepted by their serializers... but just in case, we try/catch
            try {
                if(value != key.defaultValue()) {
                    write(itoData, key.serializer(), key.id(), value);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return itoData;
    }

    public static <T> NbtCompound write(PlayerDataAttachment<T> type, T value) {
        NbtCompound itoData = new NbtCompound();

        // Values should be accepted by their serializers... but just in case, we try/catch
        try {
            write(itoData, type.serializer(), type.id(), value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return itoData;
    }

    private static <T> void write(NbtCompound root, PlayerDataAttachment.Serializer<T> serializer, String key, T value) {
        serializer.write(key, root, value);
    }

    @Nullable
    public static PlayerDataAttachment<?> get(String id) {
        return REGISTRY.get(id);
    }

    public static <T> Bounds<T> range(T min, T max) {
        return new Bounds<T>(min, max);
    }

    public void set(ServerPlayerEntity player) {

    }

    public void get(ServerPlayerEntity player) {

    }

    public record Bounds<T>(T min, T max) {

    }

    public enum SyncStrategy {
        CLIENT,
        SERVER,
        COMMON
    }

    public enum RetentionStrategy {
        RESET_ON_DEATH,
        RESET_ON_LOGOUT,
        PERSIST
    }

    public interface Serializer<T> {

        void write(String key, NbtCompound root, T value);

        T read(String key, NbtCompound root, T defaultValue);
    }

    public static class IntegerSerializer implements Serializer<Integer> {

        @Override
        public void write(String key, NbtCompound root, Integer value) {
            root.putInt(key, value);
        }

        @Override
        public Integer read(String key, NbtCompound root, Integer defaultValue) {
            if(root.contains(key)) {
                return root.getInt(key);
            }

            return defaultValue;
        }
    }
}
