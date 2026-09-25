package dev.warpersan.create_insights.tooltips.providers.create;

import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.ITooltipProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

/**
 * Provider responsible to handle {@link IHaveHoveringInformation}
 */
public class HaveHoveringInformationTooltipProvider implements ITooltipProvider
{
	@Override
	public boolean provide(GoggleTooltipCollector.TooltipContext context)
	{
		if (!context.isWearingGoggles())
			return true;

		var blockEntity = context.getBlockEntity(IHaveHoveringInformation.class);

		if (blockEntity == null)
			return true;

		var tooltip = new ArrayList<Component>();

		blockEntity.addToTooltip(tooltip, context.player().isCrouching());

		context.addAll(tooltip);

		return true;
	}
}
