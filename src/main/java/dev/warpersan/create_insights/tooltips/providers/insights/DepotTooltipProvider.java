package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.builders.ItemListBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Provider responsible to display the items on the given depots
 */
public class DepotTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected void onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var depot = context.getBlockEntity(DepotBlockEntity.class);

		if (depot == null)
			return;

		var behavior = depot.getBehaviour(DepotBehaviour.TYPE);

		if (behavior == null)
			return;

		var itemListBuilder = new ItemListBuilder()
				.addItemHandler(behavior.itemHandler);

		if (!itemListBuilder.hasStack())
			return;

		var headerBuilder = new InsightsBuilder()
				.translate("tooltip.content")
				.header();

		headerBuilder.addTo(tooltip);

		for (var component : itemListBuilder.components())
		{
			var builder = new InsightsBuilder();

			builder.add(component);

			builder.indentInto(tooltip);
		}
	}
}
