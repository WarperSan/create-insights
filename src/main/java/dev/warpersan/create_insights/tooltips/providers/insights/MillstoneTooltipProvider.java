package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.recipes.RecipeFinder;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.builders.ProgressBarBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Provider responsible to display the progress of the given millstone
 */
public class MillstoneTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected void onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var millstone = context.getBlockEntity(MillstoneBlockEntity.class);

		if (millstone == null)
			return;

		var recipe = getMillingRecipe(millstone);

		if (recipe == null)
		{
			CreateInsights.LOGGER.debug("Failed to get the recipe of the millstone.");
			return;
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

		var barBuilder = new ProgressBarBuilder()
				.ofLength(8)
				.withPrecision(0)
				.forPercentage(percent);

		var builder = new InsightsBuilder()
				.translate("tooltip.progress")
				.header();

		builder.addTo(tooltip);
		tooltip.add(barBuilder.component());
	}

	@Nullable
	private static MillingRecipe getMillingRecipe(MillstoneBlockEntity millstone)
	{
		var recipe = RecipeFinder.getRecipe(
				millstone,
				AllRecipeTypes.MILLING,
				millstone.inputInv
		);

		if (recipe instanceof MillingRecipe millingRecipe)
			return millingRecipe;

		return null;
	}
}
