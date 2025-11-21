package net.apates.herosmpmod.item;

import net.apates.herosmpmod.HeroSmpMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HeroSmpMod.MOD_ID);


    public static final Supplier<CreativeModeTab> HERO_MOD_TAB = CREATIVE_MODE_TAB.register("hero_smp",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FIERY_SWORD.get()))
                    .title(Component.translatable("creative.heromodsmp.hero_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.STEEL_DUST);
                        output.accept(ModItems.FIERY_SWORD);

                    })
                    .build());

    public static void register(IEventBus eventbus){
        CREATIVE_MODE_TAB.register(eventbus);

    }

}
