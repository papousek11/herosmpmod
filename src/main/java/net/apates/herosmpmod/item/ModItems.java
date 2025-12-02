package net.apates.herosmpmod.item;

import net.apates.herosmpmod.HeroSmpMod;
import net.apates.herosmpmod.abilities.Fire;
import net.apates.herosmpmod.huds.FireSwordHud;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.GameRules;
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
            () -> new SwordItem(ModToolTiers.MATERIAL_FOR_ALL, 3f, -3, new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("herosmpmod:fiery_sword")))){
                public boolean is_charded_1 = true;
                public int temp;
                public boolean temp2 = false;
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
                    if(temp2){
                        if (!level.isClientSide() && entity instanceof Player player) {
                            Fire.handleServer(player);
                            temp2 =false;
                        }
                    }
                    if(is_charded_1 == false){
                        temp = temp + 1;
                        FireSwordHud.bringus(false);
                        FireSwordHud.john_bringus(temp/20);
                        System.out.println(temp / 20);
                        if(temp == 400){

                            is_charded_1 = true;
                            temp = 0;
                        }
                    }
                    else{
                        FireSwordHud.bringus(true);


                    }
                    // na servu
                    //en: runs only on sever
                    if (level.isClientSide) return;

                    //I hate java why doesn´t it work without the instance I hate this shit
                    if (entity instanceof Player player) {
                        boolean isInMainHand = player.getMainHandItem() == stack;
                        if(isInMainHand){
                            FireSwordHud.ability_cas_zacal(true);
                            player.addEffect(new MobEffectInstance(
                                    MobEffects.FIRE_RESISTANCE,0,1
                            ));
                        }else{FireSwordHud.ability_cas_zacal(false);}

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
               @Override
                public InteractionResult use(Level level, Player player, InteractionHand hand) {

                    if(is_charded_1 == true){
                        //Fire.ohen();
                        temp2 = true;
                        is_charded_1 = false;

                    }

                   return null;
               }

            }
    );
    public static final DeferredItem<SwordItem> TITAN_HAMMA = ITEMS.register("titan_hama",
            () -> new SwordItem(ModToolTiers.MATERIAL_FOR_ALL, 3f, -3, new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("herosmpmod:titan_hama"))))
            {
                boolean ability = true;
                boolean charing =false;
                boolean activated = false;
                int timer= 0;
                @Override
                public boolean isBarVisible(ItemStack stack) {
                    stack.setDamageValue(-1);

                    return false; // hides the durability bar
                }
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    //stun palyer
                    if(activated){
                        ability = false;
                        activated = false;
                        charing = true;
                        target.addEffect(new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN,3*20,9999
                        ));
                        target.addEffect(new MobEffectInstance(
                                MobEffects.BLINDNESS,3*20,9999
                        ));
                        target.addEffect(new MobEffectInstance(
                                MobEffects.DARKNESS,3*20,9999
                        ));

                    }


                    return super.hurtEnemy(stack, target, attacker);
                }
                @Override
                public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected)
                {
                    if(charing){
                        if(timer != 400){
                            timer++;
                        }
                        else{
                            ability = true;
                        }
                    }
                    if (entity instanceof Player player) {
                        boolean isInMainHand = player.getMainHandItem() == stack;
                        if(isInMainHand){
                            player.addEffect(new MobEffectInstance(
                                    MobEffects.MOVEMENT_SLOWDOWN,0,1
                            ));
                        }


                    }

                }
                @Override
                public InteractionResult use(Level level, Player player, InteractionHand hand) {
                    if(ability){
                        activated = true;
                        timer = 0;
                    }

                    return null;
                }
            }
    );

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }


}
