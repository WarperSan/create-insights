package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.redstone.diodes.BrassDiodeBlockEntity;
import com.simibubi.create.content.redstone.diodes.PulseRepeaterBlockEntity;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.builders.TimeBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Provider responsible to display the time until the next pules for pulse repeaters
 */
public class PulseRepeaterTooltipProvider extends BrassDiodeTooltipProvider
{
	@Override
	protected boolean onProvide(BrassDiodeBlockEntity blockEntity, GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		if (!(blockEntity instanceof PulseRepeaterBlockEntity pulseRepeater))
			return true;

		var time = getMaxTime(pulseRepeater);

		if (time == null)
			return true;

		var percent = 1 - pulseRepeater.getProgress();

		var headerBuilder = new InsightsBuilder()
				.translate("tooltip.timing")
				.header();

		headerBuilder.addTo(tooltip);

		var timeDisplay = getTimeDisplay(time, percent);

		var builder = new InsightsBuilder();
		builder.add(timeDisplay);
		builder.indentInto(tooltip);
		return true;
	}

	/**
	 * Creates a component for the given time in ticks
	 */
	private static Component getTimeDisplay(int ticks, float progress)
	{
		if (progress == 0.0 || progress == 1.0)
		{
			var waitBuilder = new InsightsBuilder();

			waitBuilder.translate("tooltip.timing.wait")
					.style(ChatFormatting.DARK_GRAY);

			return waitBuilder.component();
		}

		var currentTicks = (int) Math.floor(ticks * progress);

		var timeBuilder = new TimeBuilder()
				.addScale(TimeBuilder.TimeScale.HOURS)
				.addScale(TimeBuilder.TimeScale.MINUTES)
				.addScale(TimeBuilder.TimeScale.SECONDS)
				.addScale(TimeBuilder.TimeScale.TICKS)
				.forTicks(currentTicks);

		var time = timeBuilder.component().withStyle(ChatFormatting.DARK_AQUA);

		var builder = new InsightsBuilder();

		builder.translate("tooltip.timing.pulse")
				.style(ChatFormatting.DARK_GRAY);

		builder.text(" ");

		builder.add(time);

		return builder.component();
	}
}
