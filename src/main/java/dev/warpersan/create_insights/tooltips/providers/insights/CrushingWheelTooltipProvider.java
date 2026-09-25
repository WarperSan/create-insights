package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.structures.StructureFinder;
import dev.warpersan.create_insights.tooltips.ProgressBarTooltip;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Provider responsible to display the progress of the given crushing wheel
 */
public class CrushingWheelTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var crushingWheel = context.getBlockEntity(CrushingWheelBlockEntity.class);

		if (crushingWheel == null)
			return true;

		var controller = StructureFinder.getCrushingWheelController(crushingWheel);

		if (controller == null)
			return true;

		if (!controller.isOccupied())
			return true;

		var recipe = getCrushingRecipe(controller);

		if (recipe == null) {
			CreateInsights.LOGGER.debug("Failed to get the recipe of the crushing wheel.");
			return true;
		}

		var total = recipe.getProcessingDuration() - 20;
		var current = Math.max(total - controller.inventory.remainingTime + 20, 0);

		if (total <= 0) {
			throw new IllegalArgumentException(
					String.format(
							"Instance of '%s' has a recipe with a processing duration less than 1.",
							CrushingWheelBlockEntity.class
					)
			);
		}

		var percent = Math.clamp((double) current / total, 0.0, 1.0);

		var bar = ProgressBarTooltip.getColoredBar(8, percent);

		var builder = new InsightsBuilder()
				.translate("tooltip.progress")
				.header();

		builder.addTo(tooltip);
		tooltip.add(bar);

		return true;
	}

	@Nullable
	private static StandardProcessingRecipe<RecipeWrapper> getCrushingRecipe(CrushingWheelControllerBlockEntity crushingWheel) {
		var recipe = crushingWheel.findRecipe();

		return recipe.map(RecipeHolder::value).orElse(null);
	}
}
