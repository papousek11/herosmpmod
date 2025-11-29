package net.apates.herosmpmod.abilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

public class FireClientSide {
    public static void client_hadling(Player player,BlockPos pos){
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        var level2 = mc.level;
        level2.setBlock(pos, Blocks.FIRE.defaultBlockState(), 1,1);

    }
}
