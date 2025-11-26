package net.tuga4life.emerald_vault.item;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// Implementa ICurioItem para usar o slot de acessórios
public class EmeraldVaultItem extends Item implements ICurioItem {

    public EmeraldVaultItem(Properties properties) {
        super(properties);
    }

    // Permite que o cofre seja equipado em qualquer slot Curios vazio
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
