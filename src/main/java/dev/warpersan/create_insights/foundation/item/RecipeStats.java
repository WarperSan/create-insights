package dev.warpersan.create_insights.foundation.item;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.simibubi.create.foundation.utility.CreateLang;

import dev.warpersan.create_insights.CreateInsights;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.minecraft.network.chat.Component;

public class RecipeStats implements TooltipModifier {
    protected final Block block;

    public RecipeStats(Block block) {
        this.block = block;
    }

    @Nullable
    public static RecipeStats create(Item item) {
        CreateInsights.LOGGER.info(item.getDescriptionId());
        if (!(item instanceof BlockItem blockItem))
            return null;

        var block = blockItem.getBlock();

        if (block instanceof IRotate || block instanceof SteamEngineBlock)
            return new RecipeStats(block);

        return null;
    }

    @Override
    public void modify(ItemTooltipEvent context) {
        var stats = getStats();

        if (stats.isEmpty())
            return;

        var tooltip = context.getToolTip();
        tooltip.add(CommonComponents.EMPTY);
        tooltip.addAll(stats);
    }

    public static List<Component> getStats() {
        var list = new ArrayList<Component>();

        var builder = CreateLang.builder()
                .add(CreateLang.text(TooltipHelper.makeProgressBar(3, IRotate.StressImpact.HIGH.ordinal() + 1))
                .style(IRotate.StressImpact.HIGH.getAbsoluteColor()));

        builder.add(CreateLang.number(10))
                .text("x ")
                .addTo(list);

        return list;
    }
}
