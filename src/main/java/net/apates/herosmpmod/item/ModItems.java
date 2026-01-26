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
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;

import java.awt.*;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HeroSmpMod.MOD_ID);


    public static final DeferredItem<Item> STEEL_DUST = ITEMS.register("steel_dust",
            () -> new Item(new Item.Properties().useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("herosmpmod:steel_dust")))));


    public static final DeferredItem<SwordItem> FIERY_SWORD = ITEMS.register("fiery_sword",
            () -> new SwordItem(ModToolTiers.MATERIAL_FOR_ALL, 3f, -3,
                    new Item.Properties()
                            .useItemDescriptionPrefix()
                            .setId(ResourceKey.create(Registries.ITEM,
                                    ResourceLocation.parse("herosmpmod:fiery_sword")))) {

                private static final String NBT_CHARGED = "Charged";
                private static final String NBT_COOLDOWN = "Cooldown";
                private static final String NBT_TRIGGER = "Trigger";

                private CompoundTag getTag(ItemStack stack) {
                    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                    return data == null ? new CompoundTag() : data.copyTag();
                }

                private void saveTag(ItemStack stack, CompoundTag tag) {
                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                }

                private void init(ItemStack stack) {
                    CompoundTag tag = getTag(stack);
                    if (!tag.contains(NBT_CHARGED)) {
                        tag.putBoolean(NBT_CHARGED, true);
                        tag.putInt(NBT_COOLDOWN, 0);
                        tag.putBoolean(NBT_TRIGGER, false);
                        saveTag(stack, tag);
                    }
                }

                @Override
                public boolean isBarVisible(ItemStack stack) {
                    return false;
                }
                //skamboard
                @Override
                public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
                    if (!(entity instanceof Player player)) return;

                    init(stack);
                    //every time i rewrite this there are less and less comments
                    // CLIENT (not tuff)
                    if (level.isClientSide) {
                        boolean inHand = player.getMainHandItem() == stack;
                        FireSwordHud.bringus(true);
                        FireSwordHud.ability_cas_zacal(inHand);
                        return;
                    }

                    // SERVER (yipee)
                    CompoundTag tag = getTag(stack);

                    if (tag.getBoolean(NBT_TRIGGER)) {
                        Fire.handleServer(player);
                        tag.putBoolean(NBT_TRIGGER, false);
                    }

                    if (!tag.getBoolean(NBT_CHARGED)) {
                        int cd = tag.getInt(NBT_COOLDOWN) + 1;
                        tag.putInt(NBT_COOLDOWN, cd);
                        if (cd >= 400) {
                            tag.putBoolean(NBT_CHARGED, true);
                            tag.putInt(NBT_COOLDOWN, 0);
                        }
                    }

                    saveTag(stack, tag);

                    if (player.getMainHandItem() == stack) {
                        player.addEffect(new MobEffectInstance(
                                MobEffects.FIRE_RESISTANCE, 1, 1));
                    }
                }

                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    target.setRemainingFireTicks(8 * 20);
                    return super.hurtEnemy(stack, target, attacker);
                }

                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                            java.util.List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.herosmpmod.fiery_sword.tooltip"));
                    super.appendHoverText(stack, context, tooltip, flag);
                }

                @Override
                public InteractionResult use(Level level, Player player, InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);
                    init(stack);

                    if (!level.isClientSide) {
                        CompoundTag tag = getTag(stack);
                        if (tag.getBoolean(NBT_CHARGED)) {
                            tag.putBoolean(NBT_TRIGGER, true);
                            tag.putBoolean(NBT_CHARGED, false);
                            tag.putInt(NBT_COOLDOWN, 0);
                            saveTag(stack, tag);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            );
    public static final DeferredItem<SwordItem> TITAN_HAMMA = ITEMS.register("titan_hama",
            () -> new SwordItem(ModToolTiers.MATERIAL_FOR_ALL, 3f, -3,
                    new Item.Properties()
                            .useItemDescriptionPrefix()
                            .setId(ResourceKey.create(Registries.ITEM,
                                    ResourceLocation.parse("herosmpmod:titan_hama")))) {

                private CompoundTag getTag(ItemStack stack) {
                    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                    return data == null ? new CompoundTag() : data.copyTag();
                }

                private void saveTag(ItemStack stack, CompoundTag tag) {
                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                }
                //every day we are closer to a gloabl war
                private void init(ItemStack stack) {
                    CompoundTag tag = getTag(stack);
                    if (!tag.contains("ability")) {
                        tag.putBoolean("ability", true);
                        tag.putBoolean("charing", false);
                        tag.putBoolean("activated", false);
                        tag.putInt("timer", 0);
                        saveTag(stack, tag);
                    }
                }

                @Override
                public boolean isBarVisible(ItemStack stack) {
                    return false;
                }

                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                            java.util.List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.herosmpmod.titan_hama.tooltip"));
                    super.appendHoverText(stack, context, tooltip, flag);
                }

                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    init(stack);
                    CompoundTag tag = getTag(stack);

                    if (tag.getBoolean("activated")) {
                        tag.putBoolean("ability", false);
                        tag.putBoolean("activated", false);
                        tag.putBoolean("charing", true);
                        saveTag(stack, tag);

                        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3 * 20, 9999));
                        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 3 * 20, 9999));
                        target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 3 * 20, 9999));
                    }

                    return super.hurtEnemy(stack, target, attacker);
                }

                @Override
                public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
                    if (!(entity instanceof Player player)) return;
                    init(stack);

                    CompoundTag tag = getTag(stack);

                    if (tag.getBoolean("charing")) {
                        int t = tag.getInt("timer") + 1;
                        tag.putInt("timer", t);
                        if (t >= 400) {
                            tag.putBoolean("ability", true);
                            tag.putBoolean("charing", false);
                        }
                        saveTag(stack, tag);
                    }

                    if (player.getMainHandItem() == stack) {
                        player.addEffect(new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN, 1, 1));
                    }
                }

                @Override
                public InteractionResult use(Level level, Player player, InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);
                    init(stack);

                    if (getTag(stack).getBoolean("ability")) {
                        CompoundTag tag = getTag(stack);
                        tag.putBoolean("activated", true);
                        tag.putBoolean("charing", true);
                        tag.putInt("timer", 0);
                        saveTag(stack, tag);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            );
    public static final DeferredItem<SwordItem> P_DAGGER = ITEMS.register("poison_dagger",
            () -> new SwordItem(ModToolTiers.MATERIAL_FOR_ALL, 3f, -3, new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("herosmpmod:poison_dagger")))) {

                //just rawdoged this one
                private CompoundTag getTag(ItemStack stack) {
                    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                    return data == null ? new CompoundTag() : data.copyTag();
                }

                private void saveTag(ItemStack stack, CompoundTag tag) {
                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                }

                private boolean getAbility(ItemStack stack) {
                    return getTag(stack).getBoolean("ability");
                }

                private void setAbility(ItemStack stack, boolean value) {
                    CompoundTag tag = getTag(stack);
                    tag.putBoolean("ability", value);
                    saveTag(stack, tag);
                }

                private boolean getCharing(ItemStack stack) {
                    return getTag(stack).getBoolean("charing");
                }

                private void setCharing(ItemStack stack, boolean value) {
                    CompoundTag tag = getTag(stack);
                    tag.putBoolean("charing", value);
                    saveTag(stack, tag);
                }

                private boolean getActivated(ItemStack stack) {
                    return getTag(stack).getBoolean("activated");
                }

                private void setActivated(ItemStack stack, boolean value) {
                    CompoundTag tag = getTag(stack);
                    tag.putBoolean("activated", value);
                    saveTag(stack, tag);
                }

                private boolean getPoison(ItemStack stack) {
                    return getTag(stack).getBoolean("poison");
                }

                private void setPoison(ItemStack stack, boolean value) {
                    CompoundTag tag = getTag(stack);
                    tag.putBoolean("poison", value);
                    saveTag(stack, tag);
                }

                private int getTimer(ItemStack stack) {
                    return getTag(stack).getInt("timer");
                }

                private void setTimer(ItemStack stack, int value) {
                    CompoundTag tag = getTag(stack);
                    tag.putInt("timer", value);
                    saveTag(stack, tag);
                }

                private int getTimer2(ItemStack stack) {
                    return getTag(stack).getInt("timer2");
                }

                private void setTimer2(ItemStack stack, int value) {
                    CompoundTag tag = getTag(stack);
                    tag.putInt("timer2", value);
                    saveTag(stack, tag);
                }

                //ř (tetx)
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                            java.util.List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.herosmpmod.poison_dagger.tooltip"));
                    super.appendHoverText(stack, context, tooltip, flag);
                }


                @Override
                public boolean isBarVisible(ItemStack stack) {
                    stack.setDamageValue(-1);
                    return false;
                }

                //skamboard
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

                    if (getPoison(stack)) {
                        target.addEffect(new MobEffectInstance(
                                MobEffects.POISON, 5 * 20, 2
                        ));
                    }

                    return super.hurtEnemy(stack, target, attacker);
                }


                @Override
                public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {

                    if (getActivated(stack)) {
                        if (getTimer2(stack) != 20 * 5) {
                            setTimer2(stack, getTimer2(stack) + 1);
                            setPoison(stack, true);
                        } else {
                            setTimer2(stack, 0);
                            setActivated(stack, false);
                            setPoison(stack, false);
                            setCharing(stack, true);
                        }
                    }
                    //i would like to kill myself
                    if (getCharing(stack)) {
                        if (getTimer(stack) != 400) {
                            setTimer(stack, getTimer(stack) + 1);
                        } else {
                            setAbility(stack, true);
                            setCharing(stack, false);
                        }
                    }

                    if (entity instanceof Player player) {
                        boolean isInMainHand = player.getMainHandItem() == stack;
                        if (isInMainHand) {
                            player.removeEffect(MobEffects.POISON);
                        }
                    }
                }


                @Override
                public InteractionResult use(Level level, Player player, InteractionHand hand) {

                    ItemStack stack = player.getItemInHand(hand);

                    if (getAbility(stack)) {
                        setActivated(stack, true);
                        setTimer(stack, 0);
                        setTimer2(stack, 0);
                    }

                    return InteractionResult.SUCCESS;
                }
            }
    );
    public static final DeferredItem<SwordItem> krys464 = ITEMS.register("kris",
            () -> new SwordItem(ModToolTiers.MATERIAL_FOR_ALL, 3f, -3, new Item.Properties()
                    .useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("herosmpmod:kris")))){
                @Override
                public boolean isBarVisible(ItemStack stack) {
                    stack.setDamageValue(-1);

                    return false; // hides the durability bar
                }
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, java.util.List<Component> TooltipComponent
                        , TooltipFlag tooltipFlag){
                    TooltipComponent.add(Component.translatable("tooltip.herosmpmod.kris.tooltip"));
                    super.appendHoverText(stack, context,TooltipComponent, tooltipFlag);
                }

            }
    );
    
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }


}
