package dev.warpersan.create_insights.tooltips.builders;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Builder used to convert ticks into a readable format
 */
public class TimeBuilder
{
	/**
	 * Enum used to define the scale of the time
	 */
	public enum TimeScale
	{
		TICKS,
		SECONDS,
		MINUTES,
		HOURS
	}

	private final EnumSet<TimeScale> scale;
	private int tickAmount;

	public TimeBuilder()
	{
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
	private static MutableComponent getTime(int totalTicks, EnumSet<TimeScale> scale)
	{
		var components = new ArrayList<Component>();

		var millisecondsPerTick = 1.0 / SharedConstants.TICKS_PER_SECOND * 1000;
		var totalMilliseconds = (int) Math.floor(totalTicks * millisecondsPerTick);
		var totalSeconds = totalMilliseconds / 1000;

		// Display hours
		if (scale.contains(TimeScale.HOURS))
		{
			var hours = totalSeconds / 3600;

			if (hours > 0)
			{
				var builder = new UnitBuilder()
						.ofUnit(UnitBuilder.UnitType.HOURS)
						.forValue(hours);

				components.add(builder.component());
			}
		}

		// Display minutes
		if (scale.contains(TimeScale.MINUTES))
		{
			var minutes = (totalSeconds % 3600) / 60;

			if (minutes > 0)
			{
				var builder = new UnitBuilder()
						.ofUnit(UnitBuilder.UnitType.MINUTES)
						.forValue(minutes);

				components.add(builder.component());
			}
		}

		// Display seconds
		if (scale.contains(TimeScale.SECONDS))
		{
			var seconds = totalSeconds % 60;

			if (seconds > 0)
			{
				var builder = new UnitBuilder()
						.ofUnit(UnitBuilder.UnitType.SECONDS)
						.forValue(seconds);

				components.add(builder.component());
			}
		}

		// Fallback if too small
		if (totalTicks < SharedConstants.TICKS_PER_SECOND)
		{
			UnitBuilder builder = new UnitBuilder();

			if (scale.contains(TimeScale.TICKS))
				builder.ofUnit(UnitBuilder.UnitType.TICKS).forValue(totalTicks);
			else
				builder.ofUnit(UnitBuilder.UnitType.SECONDS).forValue(0);

			components.add(builder.component());
		}

		var builder = new InsightsBuilder();

		for (int i = 0; i < components.size(); i++)
		{
			var component = components.get(i);

			builder.add(component);

			if (i != components.size() - 1)
				builder.space();
		}

		return builder.component();
	}

	/**
	 * Creates a component with the gathered information
	 */
	public MutableComponent component()
	{
		return getTime(this.tickAmount, this.scale);
	}
}
