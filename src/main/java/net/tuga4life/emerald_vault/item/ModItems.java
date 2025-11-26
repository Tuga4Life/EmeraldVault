package net.tuga4life.emerald_vault.item;

import net.tuga4life.emerald_vault.EmeraldVault;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EmeraldVaultMod.MODID);

    public static final RegistryObject<Item> EMERALD_VAULT = ITEMS.register("emerald_vault",
            () -> new EmeraldVaultItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
