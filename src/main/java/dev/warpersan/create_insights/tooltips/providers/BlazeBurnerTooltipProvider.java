package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
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

		builder.add(fuelTypeDisplay);
		builder.text(" ");
		builder.add(getTimeDisplay(time, isCreative));

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
		final var fireIcon = "\uD83D\uDD25";
		final var fireCountForSmouldering = 1;
		final var fireCountForKindled = 2;
		final var fireCountForSeething = 3;

		var fireCount = 0;
		var textColor = ChatFormatting.RESET;

		switch (fuelType)
		{
			case NONE ->
			{
				if (isCreative)
				{
					if (heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING)
						fireCount = fireCountForSeething;
					else if (heatLevel == BlazeBurnerBlock.HeatLevel.KINDLED)
						fireCount = fireCountForKindled;
					else
						fireCount = fireCountForSmouldering;

					textColor = ChatFormatting.DARK_PURPLE;
				} else
				{
					fireCount = fireCountForSmouldering;
					textColor = ChatFormatting.DARK_GRAY;
				}
			}
			case NORMAL ->
			{
				fireCount = fireCountForKindled;
				textColor = ChatFormatting.DARK_RED;
			}
			case SPECIAL ->
			{
				fireCount = fireCountForSeething;
				textColor = ChatFormatting.DARK_AQUA;
			}
		}

		var output = "";

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

		var timeBuilder = new TimeBuilder()
				.addScale(TimeBuilder.TimeScale.HOURS)
				.addScale(TimeBuilder.TimeScale.MINUTES)
				.addScale(TimeBuilder.TimeScale.SECONDS)
				.forTicks(ticks);

		var time = timeBuilder.getTime();

		if (time.isBlank())
		{
			var noneBuilder = new LangBuilder(CreateInsights.MOD_ID)
					.translate("tooltip.heating.none");

			return noneBuilder.component();
		}

		return Component.literal(time);
	}
}
