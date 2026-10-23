package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.redstone.diodes.BrassDiodeBlockEntity;
import com.simibubi.create.content.redstone.diodes.PulseRepeaterBlockEntity;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.tooltips.builders.HeaderBuilder;
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
	protected void onProvide(BrassDiodeBlockEntity blockEntity, TooltipContext context, List<Component> tooltip)
	{
		if (!(blockEntity instanceof PulseRepeaterBlockEntity pulseRepeater))
			return;

		var time = getMaxTime(pulseRepeater);

		if (time == null)
			return;

		var percent = 1 - pulseRepeater.getProgress();

		var header = new HeaderBuilder()
				.ofType(HeaderBuilder.HeaderType.TIMING)
				.component();

		tooltip.add(header);

		var timeDisplay = getTimeDisplay(time, percent);

		var builder = new InsightsBuilder();
		builder.add(timeDisplay);
		builder.indentInto(tooltip);
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

		builder.space();

		builder.add(time);

		return builder.component();
	}
}
