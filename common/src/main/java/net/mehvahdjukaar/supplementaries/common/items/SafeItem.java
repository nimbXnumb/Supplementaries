package net.mehvahdjukaar.supplementaries.common.items;


import net.mehvahdjukaar.supplementaries.common.items.tooltip_components.InventoryViewTooltip;
import net.mehvahdjukaar.supplementaries.common.utils.ItemsUtil;
import net.mehvahdjukaar.supplementaries.integration.CompatHandler;
import net.mehvahdjukaar.supplementaries.integration.QuarkClientCompat;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class SafeItem extends BlockItem {
    public SafeItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack incoming, Slot slot, ClickAction action, Player player, SlotAccess accessor) {
        return ItemsUtil.tryInteractingWithContainerItem(stack, incoming, slot, action, player, true);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        return ItemsUtil.tryInteractingWithContainerItem(stack, slot.getItem(), slot, action, player, false);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Optional<TooltipComponent> getTooltipImage(ItemStack pStack) {
        if (CompatHandler.QUARK && QuarkClientCompat.canRenderBlackboardTooltip()) {
            var container = pStack.get(DataComponents.CONTAINER);
            if (container != null && !pStack.has(DataComponents.CONTAINER_LOOT)) {
                return Optional.of(new InventoryViewTooltip(container, 27));
            }
        }
        return Optional.empty();
    }
}
