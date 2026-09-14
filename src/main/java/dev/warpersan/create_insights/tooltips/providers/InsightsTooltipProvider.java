package dev.warpersan.create_insights.tooltips.providers;

import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.ITooltipProvider;
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
	public boolean provide(GoggleTooltipCollector.TooltipContext context)
	{
		// Skip if not wearing goggles
		if (!context.isWearingGoggles())
			return true;

		var childTooltip = new ArrayList<Component>();

		var shouldContinue = onProvide(context, childTooltip);

		if (!childTooltip.isEmpty())
		{
			var tooltip = new ArrayList<Component>();

			if (context.hasTooltip())
				tooltip.add(CommonComponents.EMPTY);

			context.builder()
					.translate("gui.goggles.insights_stats")
					.forGoggles(tooltip);

			for (var component : childTooltip)
			{
				if (component == CommonComponents.EMPTY)
				{
					tooltip.add(component);
					continue;
				}

				context.builder()
						.add(component)
						.forGoggles(tooltip);
			}

			context.addAll(tooltip);
		}

		return shouldContinue;
	}

	protected abstract boolean onProvide(
			GoggleTooltipCollector.TooltipContext context,
			List<Component> tooltip
	);
}
