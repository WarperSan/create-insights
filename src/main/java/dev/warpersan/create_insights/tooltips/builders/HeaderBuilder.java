package dev.warpersan.create_insights.tooltips.builders;

import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;

/**
 * Builder used to convert headers into a readable format
 */
public class HeaderBuilder
{
	/**
	 * Enum used to define the type of the header
	 */
	public enum HeaderType
	{
		PROGRESS,
		HEATING,
		TIMING,
		CONTENT,
		USAGE,
	}

	private @Nullable HeaderType type;

	public HeaderBuilder()
	{
		this.type = null;
	}

	/**
	 * Sets the header type to the given type
	 */
	public HeaderBuilder ofType(HeaderType type)
	{
		this.type = type;
		return this;
	}

	/**
	 * Gets the display header of the given type 
	 */
	private static MutableComponent getHeaderComponent(HeaderType type)
	{
		LangBuilder builder;

		switch (type)
		{
			case PROGRESS -> builder = new InsightsBuilder().translate("tooltip.progress");
			case HEATING -> builder = new InsightsBuilder().translate("tooltip.heating");
			case TIMING -> builder = new InsightsBuilder().translate("tooltip.timing");
			case CONTENT -> builder = new InsightsBuilder().translate("tooltip.content");
			case USAGE -> builder = new InsightsBuilder().translate("tooltip.usage");
			default -> throw new IllegalStateException("Unexpected value: " + type);
		}

		builder.style(ChatFormatting.GRAY);

		return builder.component();
	}

	/**
	 * Creates a component with the gathered information
	 */
	public MutableComponent component()
	{
		if (this.type == null)
			throw new IllegalStateException("No header type was specified to builder");

		return getHeaderComponent(this.type);
	}
}
