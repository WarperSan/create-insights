package dev.warpersan.create_insights.tooltips.builders;

import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

/**
 * Builder used to convert a percentage into a progress bar
 */
public class ProgressBarBuilder
{
	private int length;
	private double percentage;
	private int precision;

	public ProgressBarBuilder()
	{
	}

	/**
	 * Sets the length of the progress bar
	 */
	public ProgressBarBuilder ofLength(int length)
	{
		if (length <= 0)
			throw new IllegalArgumentException("Length must be at least one.");

		this.length = length;
		return this;
	}

	/**
	 * Sets the percentage of the progress bar
	 */
	public ProgressBarBuilder forPercentage(double percentage)
	{
		if (percentage < 0.0 || percentage > 1.0)
			throw new IllegalArgumentException("Percentage must be between 0 and 1.");

		this.percentage = percentage;
		return this;
	}

	/**
	 * Sets the precision of the progress bar
	 */
	public ProgressBarBuilder withPrecision(int precision)
	{
		if (precision < 0)
			throw new IllegalArgumentException("Precision cannot be less than zero.");

		this.precision = precision;
		return this;
	}

	/**
	 * Gets the progress bar of the given length for the given percentage 
	 */
	private static MutableComponent getProgressBar(int length, double percentage, int precision)
	{
		var builder = new InsightsBuilder();

		var bar = TooltipHelper.makeProgressBar(
				length,
				(int) (length * percentage)
		);

		var format = "%3." + precision + "f%%";

		builder.add(CreateLang.text(bar));
		builder.text(" ");
		builder.text(String.format(format, percentage * 100));

		if (percentage <= 0.25)
			builder.style(ChatFormatting.DARK_RED);
		else if (percentage <= 0.50)
			builder.style(ChatFormatting.RED);
		else if (percentage <= 0.75)
			builder.style(ChatFormatting.GOLD);
		else if (percentage < 1)
			builder.style(ChatFormatting.GREEN);
		else
			builder.style(ChatFormatting.DARK_GREEN);

		return builder.component();
	}

	/**
	 * Creates a component with the gathered information
	 */
	public MutableComponent component()
	{
		return getProgressBar(
				this.length,
				this.percentage,
				this.precision
		);
	}
}
