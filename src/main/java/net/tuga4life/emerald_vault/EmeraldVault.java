// Conteúdo de src/main/java/net/tuga4life/emerald_vault/EmeraldVault.java

package net.tuga4life.emerald_vault; // <--- PACOTE RAIZ

import net.tuga4life.emerald_vault.capability.IEmeraldStorage; // Já corrigido
import net.tuga4life.emerald_vault.item.ModItems;
import net.tuga4life.emerald_vault.network.NetworkHandler;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// 1. O nome da classe deve ser "EmeraldVault" (como o ficheiro)
@Mod(EmeraldVault.MOD_ID) // 2. O seu MOD_ID é MOD_ID, não MOD_ID
public class EmeraldVault {
    // 3. O seu Mod ID é MOD_ID (usado no seu mods.toml)
    public static final String MOD_ID = "emerald_vault";

    public EmeraldVault() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 1. Registo de Itens (ModItems.java deve conter o DeferredRegister)
        ModItems.register(modEventBus);

        // 2. Registo de Capabilities
        modEventBus.addListener(this::onRegisterCapabilities);

        // 3. Registo de Pacotes de Rede
        NetworkHandler.register();
    }

    private void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IEmeraldStorage.class);
    }
}
