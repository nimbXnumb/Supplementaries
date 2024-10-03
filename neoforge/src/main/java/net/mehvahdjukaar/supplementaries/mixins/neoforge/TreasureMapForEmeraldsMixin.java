package net.mehvahdjukaar.supplementaries.mixins.neoforge;

import net.mehvahdjukaar.supplementaries.configs.CommonConfigs;
import net.mehvahdjukaar.supplementaries.integration.CompatHandler;
import net.mehvahdjukaar.supplementaries.integration.QuarkCompat;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;
import java.util.Optional;

@Mixin(targets = {"net.minecraft.world.entity.npc.VillagerTrades$TreasureMapForEmeralds"})
public abstract class TreasureMapForEmeraldsMixin {
    @Shadow
    @Final
    private TagKey<Structure> destination;

    @Final
    @Shadow
    private Holder<MapDecorationType> destinationType;

    @Final
    @Shadow
    private int emeraldCost;
    @Final
    @Shadow
    private String displayName;
    @Final
    @Shadow
    private int maxUses;
    @Final
    @Shadow
    private int villagerXp;

    @Inject(method = "getOffer", at = @At("HEAD"), cancellable = true)
    public void turnToQuill(Entity trader, RandomSource random, CallbackInfoReturnable<MerchantOffer> cir) {
        if (trader.level() instanceof ServerLevel serverLevel) {
            if (CompatHandler.QUARK && CommonConfigs.Tweaks.REPLACE_VANILLA_MAPS.get()) {
                ResourceLocation decoration = this.destinationType.getKey().location();
                ItemStack map = QuarkCompat.makeAdventurerQuill(serverLevel, this.destination,
                        100, true, 2, decoration, null, 0);
                map.set(DataComponents.CUSTOM_NAME, Component.translatable(this.displayName));
                int uses = 2;
                int xp = (int) ((this.villagerXp * this.maxUses) / (float) uses);
                int cost =  (this.emeraldCost * 1);
                cir.setReturnValue(new MerchantOffer(new ItemCost(Items.EMERALD, cost),
                        Optional.of(new ItemCost(Items.COMPASS)), map, uses, xp, 0.2F));
            }
        }
    }
}