package net.apates.herosmpmod.item;

import net.apates.herosmpmod.util.ModTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.SimpleTier;
import net.minecraft.tags.TagKey;


public class ModToolTiers {
    public static final ToolMaterial MATERIAL_FOR_ALL= new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_HERO_SMP_TOOLS, -1, 4f, 10, 0,
            ModTags.Items.HERO_REPAIR_ITEMS);
}
