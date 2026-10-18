package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Provider responsible to display how far the fans push
 */
public class EncasedFanTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected void onProvide(TooltipContext context, List<Component> tooltip)
	{
		var encasedFan = context.getBlockEntity(EncasedFanBlockEntity.class);

		if (encasedFan == null)
			return;

		var maxDistance = encasedFan.airCurrent.maxDistance;
		
		var headerBuilder = new InsightsBuilder();
		
		headerBuilder.translate("tooltip.pushing")
				.header();
		
		headerBuilder.addTo(tooltip);

		var builder = new InsightsBuilder();

		builder.translate("tooltip.pushing.distance")
				.style(ChatFormatting.DARK_GRAY);
		
		builder.space();
		builder.text(ChatFormatting.DARK_AQUA, String.format("%.2f", maxDistance));
		builder.space();
		builder.translate("tooltip.pushing.distance.unit");

		builder.indentInto(tooltip);
	}
}
