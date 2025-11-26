package net.tuga4life.emerald_vault.network;

// Classe simples para guardar a contagem sincronizada no lado do cliente
public class ClientData {
    private static long emeraldCount = 0;

    public static long getEmeraldCount() {
        return emeraldCount;
    }

    public static void setEmeraldCount(long count) {
        emeraldCount = count;
    }
}
