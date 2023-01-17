package draylar.intotheomega.api.data.player;

public interface PlayerDataAccess {

    default <T> void setPlayerData(PlayerDataAttachment<T> type, T value) {
        throw new IllegalStateException();
    }

    default <T> T getPlayerData(PlayerDataAttachment<T> type) {
        throw new IllegalStateException();
    }

    default <T> void resetPlayerData(PlayerDataAttachment<T> type) {
        throw new IllegalStateException();
    }

    default <T> void incrementPlayerData(PlayerDataAttachment<T> type) {
        throw new IllegalStateException();
    }

    default <T> void incrementPlayerData(PlayerDataAttachment<T> type, T amount) {
        throw new IllegalStateException();
    }

    default <T> void sync(PlayerDataAttachment<T> value) {

    }
}
