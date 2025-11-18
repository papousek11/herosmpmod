package net.apates.herosmpmod.item;

import net.apates.herosmpmod.HeroSmpMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HeroSmpMod.MOD_ID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }


}
