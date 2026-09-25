package dev.warpersan.create_insights.tooltips.providers;

import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.ITooltipProvider;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles insights related things for other providers
 */
public abstract class InsightsTooltipProvider implements ITooltipProvider
{
	@Override
	public void provide(GoggleTooltipCollector.TooltipContext context)
	{
		// Skip if not wearing goggles
		if (!context.isWearingGoggles()) return;

		var childTooltip = new ArrayList<Component>();

		onProvide(context, childTooltip);

		if (childTooltip.isEmpty())
			return;

		var tooltip = new ArrayList<Component>();

		if (context.hasTooltip()) tooltip.add(CommonComponents.EMPTY);

		var headerBuilder = new InsightsBuilder().translate("gui.goggles.insights_stats");

		headerBuilder.forGoggles(tooltip);

		for (var component : childTooltip)
		{
			if (component == CommonComponents.EMPTY)
			{
				tooltip.add(component);
				continue;
			}

			var subBuilder = new InsightsBuilder().add(component);

			subBuilder.forGoggles(tooltip);
		}

		context.addAll(tooltip);
	}

	protected abstract boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip);
}
