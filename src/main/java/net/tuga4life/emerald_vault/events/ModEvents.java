package net.tuga4life.emerald_vault.events;

import net.tuga4life.emerald_vault.EmeraldVault;
import net.tuga4life.emerald_vault.capability.EmeraldStorage;
import net.tuga4life.emerald_vault.capability.IEmeraldStorage;
import net.tuga4life.emerald_vault.item.ModItems;
import net.tuga4life.emerald_vault.network.NetworkHandler;
import net.tuga4life.emerald_vault.network.SyncEmeraldCountPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = EmeraldVault.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    public static final Capability<IEmeraldStorage> EMERALD_STORAGE_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<IEmeraldStorage>() {});

    // Nota: O aviso de 'deprecated' aqui é normal na 1.20.1, pode ignorar.
    private static final ResourceLocation EMERALD_VAULT_CAP_RL =
            new ResourceLocation(EmeraldVault.MOD_ID, "emerald_vault_storage");

    // Anexa a Capability de armazenamento ao jogador
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            // Se o jogador ainda não tiver a capability, anexe-a
            if (!event.getObject().getCapability(EMERALD_STORAGE_CAPABILITY).isPresent()) {

                ICapabilitySerializable<CompoundTag> provider = new ICapabilitySerializable<CompoundTag>() {
                    private final EmeraldStorage storage = new EmeraldStorage();
                    private final LazyOptional<IEmeraldStorage> optional = LazyOptional.of(() -> storage);

                    @Nonnull
                    @Override
                    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
                        return cap == EMERALD_STORAGE_CAPABILITY ? optional.cast() : LazyOptional.empty();
                    }

                    @Override
                    public CompoundTag serializeNBT() {
                        return storage.serializeNBT();
                    }

                    @Override
                    public void deserializeNBT(CompoundTag nbt) {
                        storage.deserializeNBT(nbt);
                    }
                };

                event.addCapability(EMERALD_VAULT_CAP_RL, provider);
            }
        }
    }

    // Copia o valor da Capability quando o jogador ressuscita
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(EMERALD_STORAGE_CAPABILITY).ifPresent(oldStore -> {
                event.getEntity().getCapability(EMERALD_STORAGE_CAPABILITY).ifPresent(newStore -> {
                    newStore.deserializeNBT(oldStore.serializeNBT());
                });
            });
        }
    }

    // Coleta Automática de Esmeraldas
    @SubscribeEvent
    public static void onItemPickup(ItemPickupEvent event) {
        Player player = event.getEntity();

        // Apenas processamos no servidor
        if (player.level().isClientSide) return;

        ItemEntity itemEntity = event.getOriginalEntity();

        // 1. Verificar se é uma Esmeralda
        if (!itemEntity.getItem().is(Items.EMERALD)) {
            return;
        }

        // 2. Verificar se o Cofre está equipado usando a API Curios
        // A linha estava partida, corrigi a formatação aqui:
        boolean hasVaultEquipped = CuriosApi.getCuriosHelper() // <--- Chame getCuriosHelper() diretamente
                .findEquippedCurio(ModItems.EMERALD_VAULT.get(), player)
                .isPresent();

        if (hasVaultEquipped) {

            // 3. Adicionar as esmeraldas à Capability do jogador
            int amount = itemEntity.getItem().getCount();

            player.getCapability(EMERALD_STORAGE_CAPABILITY).ifPresent(storage -> {
                storage.addEmeralds(amount);

                // 4. Marcar o evento como ALLOW para processar a coleta
                event.setResult(Result.ALLOW);

                // 5. Remover o itemEntity do mundo (destruir a esmeralda do chão)
                itemEntity.remove(Entity.RemovalReason.DISCARDED);

                // 6. Enviar o pacote de sincronização (apenas se for um ServerPlayer)
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(
                            new SyncEmeraldCountPacket(storage.getEmeraldCount()),
                            serverPlayer
                    );
                }
            });
        }
    }
}