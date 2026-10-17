package dev.warpersan.create_insights.tooltips.providers;

import dev.warpersan.create_insights.api.ITooltipProvider;
import dev.warpersan.create_insights.api.TooltipContext;
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
	public List<Component> provide(TooltipContext context)
	{
		// Skip if not wearing goggles
		if (!context.isWearingGoggles())
			return List.of();

		var childTooltip = new ArrayList<Component>();

		onProvide(context, childTooltip);

		var tooltip = new ArrayList<Component>();

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

		return tooltip;
	}

	protected abstract void onProvide(TooltipContext context, List<Component> tooltip);
}
