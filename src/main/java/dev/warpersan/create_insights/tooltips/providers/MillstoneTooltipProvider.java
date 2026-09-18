package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.recipes.RecipeFinder;
import dev.warpersan.create_insights.tooltips.ProgressBarTooltip;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import net.minecraft.network.chat.Component;

import java.util.List;

import static net.minecraft.ChatFormatting.GRAY;

/**
 * Provider responsible to display the progress of the given millstone
 */
public class MillstoneTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var millstone = context.getBlockEntity(MillstoneBlockEntity.class);

		if (millstone == null)
			return true;

		var recipe = RecipeFinder.getMillingRecipe(millstone);

		if (recipe == null)
		{
			CreateInsights.LOGGER.debug("Failed to get the recipe of the millstone.");
			return true;
		}

		var total = recipe.getProcessingDuration();
		var current = Math.max(total - millstone.timer, 0);

		if (total <= 0)
		{
			throw new IllegalArgumentException(
					String.format(
							"Instance of '%s' has a recipe with a processing duration less than 1.",
							MillstoneBlockEntity.class
					)
			);
		}

		var percent = Math.clamp((double) current / total, 0.0, 1.0);

		var bar = ProgressBarTooltip.getColoredBar(8, percent);

		var builder = new InsightsBuilder()
				.translate("tooltip.progress")
				.style(GRAY);

		builder.addTo(tooltip);
		tooltip.add(bar);

		return true;
	}
}
