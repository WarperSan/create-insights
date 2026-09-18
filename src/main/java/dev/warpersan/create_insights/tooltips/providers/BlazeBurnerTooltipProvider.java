package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.network.ClientDataCache;
import dev.warpersan.create_insights.tooltips.builders.TimeBuilder;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

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
		var heatLevel = blazeBurner.getHeatLevelFromBlock();

		var headerBuilder = context.builder()
				.translate("tooltip.heating")
				.style(ChatFormatting.GRAY);

		headerBuilder.addTo(tooltip);

		var builder = context.builder();
		var fuelTypeDisplay = getFuelTypeDisplay(
				fuelType,
				heatLevel,
				isCreative
		);
		var timeDisplay = getTimeDisplay(
				time,
				fuelType,
				isCreative
		);

		builder.add(fuelTypeDisplay);
		builder.text(" ");
		builder.add(timeDisplay);

		builder.addTo(tooltip);
		return true;
	}

	/**
	 * Creates a component for the given fuel type
	 */
	private static Component getFuelTypeDisplay(
			BlazeBurnerBlockEntity.FuelType fuelType,
			BlazeBurnerBlock.HeatLevel heatLevel,
			boolean isCreative
	)
	{
		var langKey = "";

		var textColor = ChatFormatting.RESET;

		switch (fuelType)
		{
			case NONE ->
			{
				if (isCreative)
				{
					if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING)
						langKey = "tooltip.heating.high";
					else if (heatLevel == BlazeBurnerBlock.HeatLevel.KINDLED)
						langKey = "tooltip.heating.medium";
					else
						langKey = "tooltip.heating.low";

					textColor = ChatFormatting.DARK_PURPLE;
				} else
				{
					langKey = "tooltip.heating.low";
					textColor = ChatFormatting.DARK_GRAY;
				}
			}
			case NORMAL ->
			{
				langKey = "tooltip.heating.medium";
				textColor = ChatFormatting.DARK_RED;
			}
			case SPECIAL ->
			{
				langKey = "tooltip.heating.high";
				textColor = ChatFormatting.DARK_AQUA;
			}
		}

		return new LangBuilder(CreateInsights.MOD_ID)
				.translate(langKey)
				.style(textColor)
				.component();
	}

	/**
	 * Creates a component for the given time in ticks
	 */
	private static Component getTimeDisplay(
			int ticks,
			BlazeBurnerBlockEntity.FuelType fuelType,
			boolean isCreative
	)
	{
		if (isCreative)
		{
			//noinspection UnnecessaryUnicodeEscape
			return Component.literal("\u221E");
		}

		var timeBuilder = new TimeBuilder()
				.addScale(TimeBuilder.TimeScale.HOURS)
				.addScale(TimeBuilder.TimeScale.MINUTES)
				.addScale(TimeBuilder.TimeScale.SECONDS)
				.forTicks(ticks);

		var time = timeBuilder.getTime();

		if (time.isBlank())
		{
			var noneBuilder = new LangBuilder(CreateInsights.MOD_ID);

			if (fuelType == BlazeBurnerBlockEntity.FuelType.NONE)
				noneBuilder.translate("tooltip.heating.none");
			else
				noneBuilder.text("0s");

			return noneBuilder.component();
		}

		return Component.literal(time);
	}
}
