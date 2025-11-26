package net.tuga4life.emerald_vault.network;

import net.tuga4life.emerald_vault.EmeraldVault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int nextId() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(EmeraldVault.MOD_ID, "network_channel"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE.registerMessage(nextId(),
                SyncEmeraldCountPacket.class,
                SyncEmeraldCountPacket::toBytes,
                SyncEmeraldCountPacket::fromBytes,
                SyncEmeraldCountPacket::handle,
                java.util.Optional.of(NetworkDirection.PLAY_TO_CLIENT) // <--- CORREÇÃO AQUI
        );
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}