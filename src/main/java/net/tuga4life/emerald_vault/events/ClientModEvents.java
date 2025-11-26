package net.tuga4life.emerald_vault.events;

import net.tuga4life.emerald_vault.EmeraldVault;
import net.tuga4life.emerald_vault.network.ClientData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent; // <--- ALTERADO PARA O ANTIGO
import net.minecraftforge.client.gui.overlay.ForgeGui; // <--- NOVO IMPORT NECESSÁRIO
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmeraldVault.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        // Usamos registerAboveAll para garantir que aparece por cima de tudo
        event.registerAboveAll(
                "emerald_count_hud",
                ClientModEvents::renderEmeraldCount
        );
    }

    // A assinatura deste método mudou para corresponder ao sistema antigo (recebe mais argumentos)
    private static void renderEmeraldCount(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        long count = ClientData.getEmeraldCount();

        // Só desenha se houver esmeraldas no cofre
        if (count > 0) {
            String text = "Esmeraldas no Cofre: " + count;

            Minecraft mc = Minecraft.getInstance();
            int textWidth = mc.font.width(text);

            // Posicionamento: Canto superior direito
            int x = screenWidth - textWidth - 10;
            int y = 10;

            // Desenhar o texto na tela (cor verde)
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