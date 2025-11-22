package net.apates.herosmpmod.item;

import net.apates.herosmpmod.HeroSmpMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

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
                    return false; // hides the durability bar
                }
                @Override

                public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
                    super.inventoryTick(stack, level, entity, slot, selected);

                    // jede to kazdej tick muze bejt performance problem
                    //en: runs every single tick may be a performance problem

                    // na servu
                    //en: runs only on sever
                    if (level.isClientSide) return;

                    //I hate java why doesn´t it work without the instance I hate this shit
                    if (entity instanceof Player player) {
                        boolean isInMainHand = player.getMainHandItem() == stack;
                        if(isInMainHand){
                            player.addEffect(new MobEffectInstance(
                                    MobEffects.FIRE_RESISTANCE,0,1
                            ));
                        }

                    }
                }

                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    //why did the change it to tick from seconds it makes it so much more difficul
                    //to make goddammit
                    //btw sets target on fire for 8 sec (second time ticks)
                    //why are they changing everything it is so hard to find new tutorials
                    //and the docs suck
                    target.setRemainingFireTicks(8*20);

                    return super.hurtEnemy(stack, target, attacker);
                }
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, java.util.List<Component> TooltipComponent
                    , TooltipFlag tooltipFlag){
                    TooltipComponent.add(Component.translatable("tooltip.herosmpmod.fiery_sword.tooltip"));
                    super.appendHoverText(stack, context,TooltipComponent, tooltipFlag);
                }
            });


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }


}
