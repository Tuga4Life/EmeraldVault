package net.tuga4life.emerald_vault.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class SyncEmeraldCountPacket {
    private final long emeraldCount;

    public SyncEmeraldCountPacket(long emeraldCount) {
        this.emeraldCount = emeraldCount;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeLong(this.emeraldCount);
    }

    public static SyncEmeraldCountPacket fromBytes(FriendlyByteBuf buf) {
        return new SyncEmeraldCountPacket(buf.readLong());
    }

    // Este handler é executado no lado do Cliente
    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ClientData.setEmeraldCount(this.emeraldCount);
        });
        return true;
    }
}
