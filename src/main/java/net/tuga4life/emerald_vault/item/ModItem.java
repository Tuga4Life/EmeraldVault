package net.tuga4life.emerald_vault.item;

import net.tuga4life.emerald_vault.EmeraldVault;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.math.BigInteger;

public class ModItem extends Item {

    public ModItem(Properties props) {
        super(props);
    }

    private BigInteger getCount(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        String raw = tag.getString("emeraldCount");

        if (raw == null || raw.isEmpty())
            raw = "0";

        return new BigInteger(raw);
    }

    private void setCount(ItemStack stack, BigInteger value) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("emeraldCount", value.toString());
    }

    public void addEmeralds(ItemStack stack, int amount) {
        BigInteger current = getCount(stack);
        BigInteger updated = current.add(BigInteger.valueOf(amount));

        setCount(stack, updated);
    }

    public BigInteger getEmeralds(ItemStack stack) {
        return getCount(stack);
    }
}

