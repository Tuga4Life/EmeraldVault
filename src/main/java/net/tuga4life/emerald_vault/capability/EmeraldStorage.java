package net.tuga4life.emerald_vault.capability;

import net.minecraft.nbt.CompoundTag;

public class EmeraldStorage implements IEmeraldStorage {
    private long emeraldCount = 0;

    @Override
    public long getEmeraldCount() {
        return emeraldCount;
    }

    @Override
    public long addEmeralds(int amount) {
        if (amount > 0) {
            this.emeraldCount += amount;
        }
        return this.emeraldCount;
    }

    @Override
    public void setEmeraldCount(long count) {
        this.emeraldCount = count;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("emerald_count", emeraldCount);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("emerald_count")) {
            emeraldCount = nbt.getLong("emerald_count");
        }
    }
}
