package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.logistics.tunnel.BrassTunnelBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Provider responsible to display the distribution method on the given brass tunnels
 */
public class BrassTunnelTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected void onProvide(TooltipContext context, List<Component> tooltip)
	{
		var tunnel = context.getBlockEntity(BrassTunnelBlockEntity.class);

		if (tunnel == null)
			return;

		if (!tunnel.hasDistributionBehaviour())
			return;

		var scrollValueBehaviour = tunnel.getBehaviour(ScrollOptionBehaviour.TYPE);

		if (scrollValueBehaviour == null)
			return;

		if (!(scrollValueBehaviour instanceof ScrollOptionBehaviour<?> scrollOptionBehavior))
			return;

		var rawValue = scrollOptionBehavior.get();

		if (!(rawValue instanceof BrassTunnelBlockEntity.SelectionMode value))
			return;

		var headerBuilder = new InsightsBuilder()
				.translate("tooltip.usage")
				.header();

		headerBuilder.addTo(tooltip);

		var builder = new InsightsBuilder();

		builder.translate("tooltip.usage.mode");
		builder.space();
		builder.translatable(ChatFormatting.GOLD, value.getTranslationKey());
		builder.style(ChatFormatting.DARK_GRAY);
		builder.indentInto(tooltip);
	}
}
