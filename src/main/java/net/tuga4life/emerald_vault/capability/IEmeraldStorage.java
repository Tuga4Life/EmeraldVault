package net.tuga4life.emerald_vault.capability;

import net.minecraft.nbt.CompoundTag;

public interface IEmeraldStorage {

    long getEmeraldCount();
    long addEmeralds(int amount); // Adiciona 'amount' e devolve o novo total
    void setEmeraldCount(long count);

    // Métodos para persistência de dados (guardar no disco)
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag nbt);
}