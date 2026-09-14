package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.ITooltipProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class HaveGoggleInformationTooltipProvider implements ITooltipProvider
{
	@Override
	public boolean provide(GoggleTooltipCollector.TooltipContext context)
	{
		if (!context.isWearingGoggles())
			return true;

		var blockEntity = context.getBlockEntity();

		if (!(blockEntity instanceof IHaveGoggleInformation gte))
			return true;

		var tooltip = new ArrayList<Component>();

		gte.addToGoggleTooltip(tooltip, context.player().isCrouching());

		context.addAll(tooltip);

		return true;
	}
}
