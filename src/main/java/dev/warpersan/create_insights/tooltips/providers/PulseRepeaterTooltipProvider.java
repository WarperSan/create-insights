package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.redstone.diodes.BrassDiodeBlockEntity;
import com.simibubi.create.content.redstone.diodes.PulseRepeaterBlockEntity;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.network.ClientDataCache;
import dev.warpersan.create_insights.tooltips.builders.TimeBuilder;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Provider responsible to display the time until the next pules for pulse repeaters
 */
public class PulseRepeaterTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var pulseRepeater = context.getBlockEntity(PulseRepeaterBlockEntity.class);

		if (pulseRepeater == null)
			return true;

		var maxTimeValue = ClientDataCache.getOrRequest(
				pulseRepeater.getBlockPos(),
				BrassDiodeBlockEntity.class,
				"maxState.value"
		);

		if (maxTimeValue == null)
			return true;

		var time = Integer.parseInt(maxTimeValue);
		var percent = 1 - pulseRepeater.getProgress();

		var headerBuilder = context.builder()
				.translate("tooltip.timing")
				.style(ChatFormatting.GRAY);

		headerBuilder.addTo(tooltip);

		tooltip.add(getTimeDisplay(time, percent));
		return true;
	}

	/**
	 * Creates a component for the given time in ticks
	 */
	private static Component getTimeDisplay(int ticks, float progress)
	{
		if (progress == 0.0 || progress == 1.0)
		{
			var waitBuilder = new LangBuilder(CreateInsights.MOD_ID);

			waitBuilder.translate("tooltip.timing.wait")
					.style(ChatFormatting.DARK_GRAY);

			return waitBuilder.component();
		}
		
		var currentTicks = (int) Math.floor(ticks * progress);

		var timeBuilder = new TimeBuilder()
				.addScale(TimeBuilder.TimeScale.HOURS)
				.addScale(TimeBuilder.TimeScale.MINUTES)
				.addScale(TimeBuilder.TimeScale.SECONDS)
				.forTicks(currentTicks);

		var time = timeBuilder.getTime();

		var builder = new LangBuilder(CreateInsights.MOD_ID);

		builder.translate("tooltip.timing.pulse")
				.style(ChatFormatting.DARK_GRAY);

		builder.text(" ");

		builder.text(ChatFormatting.DARK_AQUA, time);

		return builder.component();
	}
}
