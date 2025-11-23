package net.apates.herosmpmod.abilities;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;

public class Fire {


    public static void ohen(){
        System.out.println("baller");
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;




        var player = mc.player;
        var level = mc.level;




        double px2 = player.getX();
        double pz2 = player.getZ();

        BlockPos pos = BlockPos.containing(px2, player.getY(), pz2);
        if (level.getBlockState(pos).isAir() &&
                level.getBlockState(pos.below()).isSolid()) {

            level.setBlock(pos, Blocks.SOUL_FIRE.defaultBlockState(), 20, 10);

        }





        double radius = 1.0;
        for (int i = 0; i < 8; i++) {
            double angle = (Math.PI * 2 / 8) * i;

            double px = player.getX() + Math.cos(angle) * radius;
            double py = player.getY() + 1.0;   // height around head
            double pz = player.getZ() + Math.sin(angle) * radius;

            level.addParticle(ParticleTypes.FLAME, px, py, pz, 0, 0, 0);
            level.addParticle(ParticleTypes.FLAME, px +5 , py, pz, 0, 5, 0);
            level.addParticle(ParticleTypes.FLAME, px +1 , py +1, pz +1, 1, 0, 1);
            level.addParticle(ParticleTypes.LAVA, px, py, pz, 0, 0, 0);






        }
        double radius_fireball = 9.0;
//        int points = 24; // smooth ring


            // Only set fire if air above a solid block



    }





}
