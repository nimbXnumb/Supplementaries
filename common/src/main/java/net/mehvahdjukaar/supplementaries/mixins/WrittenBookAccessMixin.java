package net.mehvahdjukaar.supplementaries.mixins;

import net.mehvahdjukaar.supplementaries.common.block.IAntiquable;
import net.mehvahdjukaar.supplementaries.common.items.AntiqueInkItem;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookViewScreen.WrittenBookAccess.class)
public abstract class WrittenBookAccessMixin implements IAntiquable {

    @Unique
    private boolean supplementaries$antiqueInk;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void supp$checkAntiqueInk(ItemStack itemStack, CallbackInfo ci){
        this.supplementaries$antiqueInk = AntiqueInkItem.hasAntiqueInk(itemStack);
    }

    @Override
    public boolean isAntique() {
        return supplementaries$antiqueInk;
    }

    @Override
    public void setAntique(boolean hasInk) {
    }
}
