package net.apates.herosmpmod.huds;


import com.mojang.blaze3d.vertex.PoseStack;
import net.apates.herosmpmod.HeroSmpMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = HeroSmpMod.MOD_ID, value = Dist.CLIENT)

public class FireSwordHud {
    public static boolean render_fire_ready = false;
    public static int cooldown;
    public static boolean nabijeni= false;
    public static void ability_cas_zacal(boolean temp){
        nabijeni = temp;
    }
    public static void bringus(boolean temp){
       render_fire_ready = temp;
    }
    public static void john_bringus(int temp){
         cooldown = temp;
    }

    @SubscribeEvent
    public static void onRenderHud(RenderGuiLayerEvent.Post event) {
        if(nabijeni){
            if(render_fire_ready){
                Minecraft mc = Minecraft.getInstance();
                if (mc.player == null) return;







                PoseStack pose = event.getGuiGraphics().pose();
                Font font = mc.font;



                pose.pushPose();
                event.getGuiGraphics().drawString(
                        font,
                        "ability ready",
                        10, 10,          // X/Y on the screen
                        0xFF4500         // Orange color
                );
                pose.popPose();
            }
            else {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player == null) return;







                PoseStack pose = event.getGuiGraphics().pose();
                Font font = mc.font;

                int temp = 20 - cooldown;
                String balus = String.valueOf(temp);
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



    }
}

