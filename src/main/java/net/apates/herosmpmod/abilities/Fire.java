package net.apates.herosmpmod.abilities;


import net.apates.herosmpmod.HeroSmpMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.minecraft.world.entity.player.Player;


public class Fire {


    public static void handleServer(Player player) {
        System.out.println("baller");
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;





        var player2 = mc.player;
        var level = mc.level;




        double px2 = player2.getX();
        double pz2 = player2.getZ();

        BlockPos pos = BlockPos.containing(px2 -4, player2.getY(), pz2 - 4);
        int temp = pos.getY();
        double tempx = pos.getX() ;
        double tempz = pos.getZ() ;
        double offset_y = 0;




    for(int x = -2; x < 4; x++) {
        for (int i = 0; i < 16; i = i + 1) {
            for (int y = 0; y < 16; y++) {
                //System.out.println(x + "toto x");
                pos = BlockPos.containing(tempx + i / 2, temp+x , tempz + y / 2);
                if (level.getBlockState(BlockPos.containing(tempx + i / 2, temp +x , tempz + y / 2)).isAir()  &&
                        level.getBlockState(pos.below()).isSolid()||
                        level.getBlockState(BlockPos.containing(tempx + i / 2, temp +x , tempz + y / 2)).is(Blocks.SHORT_GRASS)  &&
                        level.getBlockState(pos.below()).isSolid()) {



                    System.out.println(pos);
                    level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 20, 10);

                }

            }
        }
    }






        double radius = 1.0;
        for (int i = 0; i < 8; i++) {
            double angle = (Math.PI * 2 / 8) * i;

            double px = player2.getX() + Math.cos(angle) * radius;
            double py = player2.getY() + 1.0;   // height around head
            double pz = player2.getZ() + Math.sin(angle) * radius;

            level.addParticle(ParticleTypes.FLAME, px, py, pz, 0, 0, 0);
            level.addParticle(ParticleTypes.FLAME, px +5 , py, pz, 0, 5, 0);
            level.addParticle(ParticleTypes.FLAME, px +1 , py +1, pz +1, 1, 0, 1);
            level.addParticle(ParticleTypes.LAVA, px, py, pz, 0, 0, 0);
            level.addParticle(ParticleTypes.FLAME, px, py, pz, 6, 8, 1);
            level.addParticle(ParticleTypes.FLAME, px +8 , py, pz, 0, 5, 0);
            level.addParticle(ParticleTypes.FLAME, px +13, py +1, pz +1, 1, 0, 1);
            level.addParticle(ParticleTypes.LAVA, px, py, pz, 9, 0, 5) ;






        }








    }







}
