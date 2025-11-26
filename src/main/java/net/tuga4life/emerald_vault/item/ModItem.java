package com.tuga4life.emeraldvault;

import com.tuga4life.emeraldvault.capability.IEmeraldStorage;
import com.tuga4life.emeraldvault.item.ModItems;
import com.tuga4life.emeraldvault.network.NetworkHandler;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EmeraldVaultMod.MODID)
public class EmeraldVaultMod {
    public static final String MODID = "emeraldvault";

    public EmeraldVaultMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 1. Registo de Itens
        ModItems.register(modEventBus);

        // 2. Registo de Capabilities
        modEventBus.addListener(this::onRegisterCapabilities);

        // 3. Registo de Pacotes de Rede
        NetworkHandler.register();
    }

    private void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        // Regista a interface de armazenamento para que o Forge saiba como serializá-la
        event.register(IEmeraldStorage.class);
    }
}

