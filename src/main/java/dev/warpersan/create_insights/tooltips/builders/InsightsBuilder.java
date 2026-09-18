package dev.warpersan.create_insights.tooltips.builders;

import dev.warpersan.create_insights.CreateInsights;
import joptsimple.internal.Strings;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
	 * Adds an indent to the builder
	 */
	public void indentInto(List<? super MutableComponent> tooltip)
	{
		var builder = new InsightsBuilder();
		
		builder.text(" ").add(this);
		
		tooltip.add(builder.component());
	}
}
