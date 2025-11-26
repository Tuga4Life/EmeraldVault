package net.tuga4life.emerald_vault.network;

public class ClientData {
    private static long emeraldCount = 0;

    public static long getEmeraldCount() {
        return emeraldCount;
    }

    public static void setEmeraldCount(long count) {
        emeraldCount = count;
    }
}
