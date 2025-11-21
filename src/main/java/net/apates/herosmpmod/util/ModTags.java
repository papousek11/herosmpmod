package net.apates.herosmpmod.util;

import net.apates.herosmpmod.HeroSmpMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;




public class ModTags {

    public  static class Blocks{
    public static final TagKey<Block> IMCORRECT_FOR_HERO_SMP_TOOLS = createTag("incorrect_for_herosmp");



        public static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(HeroSmpMod.MOD_ID, name));
        }
    }




        public static class Items{
            public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("tranformable items");

            public static TagKey<Item> createTag(String name){
                return ItemTags.create(ResourceLocation.fromNamespaceAndPath(HeroSmpMod.MOD_ID, name));





        }

    }



}
