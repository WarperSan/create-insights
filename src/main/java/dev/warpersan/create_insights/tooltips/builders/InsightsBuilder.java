package dev.warpersan.create_insights.tooltips.builders;

import dev.warpersan.create_insights.CreateInsights;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Builder responsible to provide helpful methods for tooltip creation
 */
public class InsightsBuilder extends LangBuilder
{
	public InsightsBuilder()
	{
		super(CreateInsights.MOD_ID);
	}

	/**
	 * Styles the builder as a header
	 */
	public InsightsBuilder header()
	{
		this.style(ChatFormatting.GRAY);
		return this;
	}

	/**
	 * Appends a localized component
	 */
	@Override
	public @NotNull InsightsBuilder translate(@NotNull String langKey, Object @NotNull ... args)
	{
		super.translate(langKey, args);
		return this;
	}

	/**
	 * Appends a localized component
	 * @apiNote Key must be the full translation key
	 */
	@SuppressWarnings("UnusedReturnValue")
	public InsightsBuilder translatable(String translationKey, Object... args)
	{
		var component = Component.translatable(translationKey, args);

		this.add(component);
		return this;
	}

	/**
	 * Appends a localized colored component
	 * @apiNote Key must be the full translation key
	 */
	@SuppressWarnings("UnusedReturnValue")
	public InsightsBuilder translatable(ChatFormatting format, String translationKey, Object... args)
	{
		var component = Component.translatable(translationKey, args);
		component.withStyle(format);

		this.add(component);
		return this;
	}

	/**
	 * Adds a divider for fraction-based numbering
	 */
	@SuppressWarnings("UnusedReturnValue")
	public InsightsBuilder divider()
	{
		text("/");
		return this;
	}

	/**
	 * Appends a number component
	 */
	public InsightsBuilder number(double value)
	{
		text(LangNumberFormat.format(value));
		return this;
	}

	/**
	 * Appends a colored number component
	 */
	public InsightsBuilder number(ChatFormatting format, double value)
	{
		text(format, LangNumberFormat.format(value));
		return this;
	}

	/**
	 * Adds an indent to the builder
	 */
	public void indentInto(List<? super MutableComponent> tooltip)
	{
		var builder = new InsightsBuilder();

		builder.space().add(this);

		tooltip.add(builder.component());
	}
}
