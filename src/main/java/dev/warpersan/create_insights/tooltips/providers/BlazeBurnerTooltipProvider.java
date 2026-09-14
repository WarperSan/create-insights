package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.network.ClientDataCache;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;

import java.util.List;

import static net.minecraft.ChatFormatting.*;

/**
 * Provider responsible to display the heat remaining of the given blaze burner
 */
public class BlazeBurnerTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var blazeBurner = context.getBlockEntity(BlazeBurnerBlockEntity.class);

		if (blazeBurner == null)
			return true;

		var remainingBurnTime = ClientDataCache.getOrRequest(
				blazeBurner.getBlockPos(),
				BlazeBurnerBlockEntity.class,
				"remainingBurnTime"
		);

		if (remainingBurnTime == null)
			return true;

		var time = Integer.parseInt(remainingBurnTime);
		var fuelType = blazeBurner.getActiveFuel();
		var isCreative = blazeBurner.isCreative();

		var headerBuilder = context.builder()
				.translate("tooltip.heating")
				.style(GRAY);

		headerBuilder.addTo(tooltip);

		var builder = context.builder();

		builder.add(getFuelTypeDisplay(fuelType, isCreative));
		builder.text(" ");
		builder.add(getTimeDisplay(time, isCreative));

		builder.addTo(tooltip);
		return true;
	}

	/**
	 * Creates a component for the given fuel type
	 */
	private static Component getFuelTypeDisplay(BlazeBurnerBlockEntity.FuelType fuel, boolean isCreative)
	{
		int fireCount;
		var fireIcon = "\uD83D\uDD25";
		ChatFormatting textColor;

		var output = "";

		switch (fuel)
		{
			case NONE ->
			{
				if (isCreative)
				{
					fireCount = 3;
					textColor = DARK_PURPLE;
				}
				else
				{
					fireCount = 1;
					textColor = DARK_GRAY;
				}
			}
			case NORMAL ->
			{
				fireCount = 2;
				textColor = DARK_RED;
			}
			case SPECIAL ->
			{
				fireCount = 3;
				textColor = DARK_AQUA;
			}
			default ->
			{
				fireCount = 0;
				textColor = RESET;
			}
		}

		output += fireIcon.repeat(fireCount);

		return CreateLang.text(output).style(textColor).component();
	}

	/**
	 * Creates a component for the given time in ticks
	 */
	private static Component getTimeDisplay(int ticks, boolean isCreative)
	{
		if (isCreative)
		{
			//noinspection UnnecessaryUnicodeEscape
			return CreateLang.text("\u221E").component();
		}
		
		var milliseconds = (int) Math.floor(ticks / (double) SharedConstants.TICKS_PER_SECOND * 1000);
		long totalSeconds = milliseconds / 1000;

		var hours = (totalSeconds % 86400) / 3600;
		var minutes = (totalSeconds % 3600) / 60;
		var seconds = totalSeconds % 60;

		var sb = new StringBuilder();

		if (hours > 0)
			sb.append(hours).append("h ");

		if (minutes > 0)
			sb.append(minutes).append("m ");

		if (seconds > 0 || sb.isEmpty())
			sb.append(seconds).append("s");

		return CreateLang.text(sb.toString().trim()).component();
	}
}
