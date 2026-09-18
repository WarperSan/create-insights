package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Provider responsible to display how far the fans push
 */
public class EncasedFanTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var encasedFan = context.getBlockEntity(EncasedFanBlockEntity.class);

		if (encasedFan == null)
			return true;

		var maxDistance = encasedFan.airCurrent.maxDistance;
		
		var headerBuilder = new InsightsBuilder();
		
		headerBuilder.translate("tooltip.pushing")
				.header();
		
		headerBuilder.addTo(tooltip);

		var builder = new InsightsBuilder();

		builder.translate("tooltip.pushing.distance")
				.style(ChatFormatting.DARK_GRAY);
		
		builder.text(" ");
		
		builder.text(ChatFormatting.DARK_AQUA, String.format("%.2f", maxDistance));
		builder.text(" blocks");

		builder.indentInto(tooltip);

		return true;
	}
}
