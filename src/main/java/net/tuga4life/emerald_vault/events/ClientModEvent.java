package net.tuga4life.emerald_vault.events;

import com.tuga4life.emeraldvault.EmeraldVaultMod;
import com.tuga4life.emeraldvault.network.ClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiLayersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmeraldVaultMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        // Regista um novo layer de HUD
        event.register(
                new ResourceLocation(EmeraldVaultMod.MODID, "emerald_count_hud"),
                (guiGraphics, partialTick) -> renderEmeraldCount(guiGraphics)
        );
    }

    private static void renderEmeraldCount(GuiGraphics guiGraphics) {
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

            // Desenhar o texto na tela (cor verde)
            guiGraphics.drawString(
                    mc.font,
                    text,
                    x,
                    y,
                    0xFF00AA00, // Cor verde
                    true // Com sombra
            );
        }
    }
}
