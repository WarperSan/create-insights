package dev.warpersan.create_insights.tooltips.builders;

import dev.warpersan.create_insights.CreateInsights;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Builder used to convert ticks into a readable format
 */
public class TimeBuilder extends LangBuilder
{
	/**
	 * Enum used to define the scale of the time
	 */
	public enum TimeScale
	{
		MILLISECONDS,
		SECONDS,
		MINUTES,
		HOURS
	}

	private final EnumSet<TimeScale> scale;
	private int tickAmount;

	public TimeBuilder()
	{
		super(CreateInsights.MOD_ID);

		this.scale = EnumSet.noneOf(TimeScale.class);
	}

	/**
	 * Adds the given scale to the current scale
	 */
	public TimeBuilder addScale(TimeScale scale)
	{
		this.scale.add(scale);
		return this;
	}

	/**
	 * Sets the amount of time to the given amount of ticks
	 */
	public TimeBuilder forTicks(int ticks)
	{
		this.tickAmount = ticks;
		return this;
	}

	/**
	 * Gets the display time of the given amount using the given scale
	 */
	private static String getTime(int totalTicks, EnumSet<TimeScale> scale)
	{
		var parts = new ArrayList<String>();

		var millisecondsPerTick = 1.0 / SharedConstants.TICKS_PER_SECOND * 1000;
		var totalMilliseconds = (int) Math.floor(totalTicks * millisecondsPerTick);
		var totalSeconds = totalMilliseconds / 1000;

		// Display hours
		if (scale.contains(TimeScale.HOURS))
		{
			var hours = totalSeconds / 3600;

			if (hours > 0)
				parts.add(hours + "h");
		}

		// Display minutes
		if (scale.contains(TimeScale.MINUTES))
		{
			var minutes = (totalSeconds % 3600) / 60;

			if (minutes > 0)
				parts.add(minutes + "m");
		}

		// Display seconds
		if (scale.contains(TimeScale.SECONDS))
		{
			var seconds = totalSeconds % 60;

			if (seconds > 0)
				parts.add(seconds + "s");
		}

		// Display milliseconds
		if (scale.contains(TimeScale.MILLISECONDS))
		{
			var milliseconds = totalMilliseconds % 1000;

			if (milliseconds > 0)
				parts.add(milliseconds + "ms");
		}

		return String.join(" ", parts);
	}

	/**
	 * Gets the display time of the builder
	 */
	public String getTime()
	{
		return getTime(this.tickAmount, this.scale);
	}

	@Override
	public @NotNull MutableComponent component()
	{
		var time = this.getTime();

		return Component.literal(time);
	}
}
