package net.tuga4life.emerald_vault.events;

import net.tuga4life.emeraldvault.EmeraldVaultMod; // Importar a classe principal para o MODID
import net.tuga4life.emerald_vault.network.ClientData; // Importar a classe que guarda a contagem

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiLayersEvent; // <--- Import Corrigido
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmeraldVaultMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        // Usa o método de referência (ClientModEvents::renderEmeraldCount) que é mais limpo
        // O Forge associa automaticamente a GuiGraphics e o partialTick
        event.register(
                new ResourceLocation(EmeraldVaultMod.MODID, "emerald_count_hud"),
                ClientModEvents::renderEmeraldCount
        );
    }

    // A função de renderização TEM DE aceitar GuiGraphics e float (partialTick)
    private static void renderEmeraldCount(GuiGraphics guiGraphics, float partialTick) {
        long count = ClientData.getEmeraldCount();

        // Só desenha se houver esmeraldas no cofre
        if (count > 0) {
            String text = "Esmeraldas no Cofre: " + count;

            Minecraft mc = Minecraft.getInstance();
            int screenWidth = guiGraphics.guiWidth();
            int textWidth = mc.font.width(text);

            // Posicionamento: Canto superior direito
            int x = screenWidth - textWidth - 10;
            int y = 10;

            // Desenhar o texto na tela (cor verde 0xFF00AA00)
            guiGraphics.drawString(
                    mc.font,
                    text,
                    x,
                    y,
                    0xFF00AA00,
                    true // Com sombra
            );
        }
    }
}