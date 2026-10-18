package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.builders.UnitBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;

import java.util.List;

public class PumpTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected void onProvide(TooltipContext context, List<Component> tooltip)
	{
		var pump = context.getBlockEntity(PumpBlockEntity.class);

		if (pump == null)
			return;

		var speed = Math.abs(pump.getSpeed());
		var rate = Math.floor(speed * 0.5 * SharedConstants.TICKS_PER_SECOND);

		var maximumRate = new UnitBuilder()
				.ofUnit(UnitBuilder.UnitType.MILLIBUCKETS)
				.forValue(rate)
				.component()
				.withStyle(ChatFormatting.DARK_AQUA);

		var headerBuilder = new InsightsBuilder()
				.translate("tooltip.transfer")
				.header();

		headerBuilder.addTo(tooltip);
		
		var builder = new InsightsBuilder();
		
		builder.translate("tooltip.transfer.pump");
		builder.space();
		builder.add(maximumRate);
		builder.text(" / s");
		builder.style(ChatFormatting.DARK_GRAY);
		builder.indentInto(tooltip);
	}
}
