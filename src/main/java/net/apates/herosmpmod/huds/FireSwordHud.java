package net.apates.herosmpmod.huds;


import com.mojang.blaze3d.vertex.PoseStack;
import net.apates.herosmpmod.HeroSmpMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@Mod.EventBusSubscriber(modid = HeroSmpMod.MOD_ID, value = Dist.CLIENT)

public class FireSwordHud {
    @SubscribeEvent
    public static void onRenderHud(RenderGuiLayerEvent.Post event, int baller) {

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack held = mc.player.getMainHandItem();

        // Check if the player is holding *your* sword


        // Draw text
        PoseStack pose = event.getGuiGraphics().pose();
        Font font = mc.font;

        int temp = 20-baller;
        String balus = String.valueOf(baller);
                pose.pushPose();
        event.getGuiGraphics().drawString(
                font,
                balus,
                10, 10,          // X/Y on the screen
                0xFF4500         // Orange color
        );
        pose.popPose();
    }
}

