package net.tuga4life.emerald_vault.network;

import net.tuga4life.emerald_vault.EmeraldVault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int nextId() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(EmeraldVaultMod.MODID, "network_channel"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        // Registo do pacote (Servidor -> Cliente)
        INSTANCE.registerMessage(nextId(),
                SyncEmeraldCountPacket.class,
                SyncEmeraldCountPacket::toBytes,
                SyncEmeraldCountPacket::fromBytes,
                SyncEmeraldCountPacket::handle,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }
}
