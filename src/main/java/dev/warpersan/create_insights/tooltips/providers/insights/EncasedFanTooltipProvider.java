package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.tooltips.builders.HeaderBuilder;
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

		var header = new HeaderBuilder()
				.ofType(HeaderBuilder.HeaderType.USAGE)
				.component();

		tooltip.add(header);

		var builder = new InsightsBuilder();

		builder.translate("tooltip.usage.push")
				.style(ChatFormatting.DARK_GRAY);

		builder.space();
		builder.text(ChatFormatting.DARK_AQUA, String.format("%.2f", maxDistance));
		builder.space();
		builder.translate("tooltip.usage.push.unit");

		builder.indentInto(tooltip);
	}
}
