package net.apates.herosmpmod.item;

import net.apates.herosmpmod.HeroSmpMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HeroSmpMod.MOD_ID);


    public static final DeferredItem<Item> STEEL_DUST = ITEMS.register("steel_dust",
            () -> new Item(new Item.Properties().useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("herosmpmod:steel_dust")))));


    public static final DeferredItem<SwordItem> FIERY_SWORD = ITEMS.register("fiery_sword",
            () -> new SwordItem(ModToolTiers.MATERIAL_FOR_ALL, 3f, 3f, new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("herosmpmod:fiery_sword")))){


                @Override
                public boolean isBarVisible(ItemStack stack) {
                    stack.setDamageValue(-1);
                    return false; // Hides the durability bar
                }
            });


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }


}
