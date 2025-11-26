package net.tuga4life.emerald_vault.events;

import net.tuga4life.emerald_vault.EmeraldVault;
import net.tuga4life.emerald_vault.network.ClientData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiLayersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmeraldVault.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.register(
                // CORREÇÃO AQUI: Usar a classe EmeraldVault e o campo MOD_ID
                new ResourceLocation(EmeraldVault.MOD_ID, "emerald_count_hud"),
                ClientModEvents::renderEmeraldCount
        );
    }

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