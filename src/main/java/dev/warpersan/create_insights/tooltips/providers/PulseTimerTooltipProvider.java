package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.redstone.diodes.BrassDiodeBlockEntity;
import com.simibubi.create.content.redstone.diodes.PulseTimerBlockEntity;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.builders.TimeBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.DiodeBlock;

import java.util.List;

/**
 * Provider responsible to display the time until the next pules for pulse timers
 */
public class PulseTimerTooltipProvider extends BrassDiodeTooltipProvider
{
	@Override
	protected boolean onProvide(BrassDiodeBlockEntity blockEntity, GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		if (!(blockEntity instanceof PulseTimerBlockEntity pulseTimer))
			return true;

		var time = getMaxTime(pulseTimer);

		if (time == null)
			return true;

		var percent = 1 - pulseTimer.getProgress();

		var headerBuilder = new InsightsBuilder()
				.translate("tooltip.timing")
				.style(ChatFormatting.GRAY);

		headerBuilder.addTo(tooltip);

		if (pulseTimer.getBlockState().getValue(DiodeBlock.POWERED))
		{
			var disabledBuilder = new InsightsBuilder();

			disabledBuilder.translate("tooltip.timing.disabled")
					.style(ChatFormatting.DARK_GRAY);

			disabledBuilder.addTo(tooltip);
		} else
			tooltip.add(getTimeDisplay(time, percent));
		return true;
	}

	/**
	 * Creates a component for the given time in ticks
	 */
	private static Component getTimeDisplay(int ticks, float progress)
	{
		var currentTicks = (int) Math.floor(ticks * progress);

		var timeBuilder = new TimeBuilder()
				.addScale(TimeBuilder.TimeScale.HOURS)
				.addScale(TimeBuilder.TimeScale.MINUTES)
				.addScale(TimeBuilder.TimeScale.SECONDS)
				.forTicks(currentTicks);

		var time = timeBuilder.getTime();

		var builder = new InsightsBuilder();

		builder.translate("tooltip.timing.pulse")
				.style(ChatFormatting.DARK_GRAY);

		builder.text(" ");

		builder.text(ChatFormatting.DARK_AQUA, time);

		return builder.component();
	}
}
