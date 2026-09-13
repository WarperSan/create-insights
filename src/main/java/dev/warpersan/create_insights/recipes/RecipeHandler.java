package dev.warpersan.create_insights.recipes;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.structures.StructureFinder;
import dev.warpersan.create_insights.tooltips.ProgressBarTooltip;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

/**
 * Class responsible to create a recipe tooltip for blocks
 */
public class RecipeHandler {

    /**
     * Gets the recipe tooltip of the given block
     */
    @Nullable
    public static MutableComponent getRecipeTooltip(BlockEntity blockEntity) {
        try {
            if (blockEntity instanceof MillstoneBlockEntity millstone)
                return getMillstoneTooltip(millstone);

            if (blockEntity instanceof CrushingWheelBlockEntity crushingWheel)
                return getCrushingWheelTooltip(crushingWheel);

        } catch (Exception e) {
            CreateInsights.LOGGER.error("Error while getting the recipe: {}", e.getMessage());
        }

        return null;
    }

    @Nullable
    private static MutableComponent getMillstoneTooltip(MillstoneBlockEntity millstone) {
        var recipe = RecipeFinder.getMillingRecipe(millstone);

        if (recipe == null) {
            CreateInsights.LOGGER.debug("Failed to get the recipe of the millstone.");
            return null;
        }

        var total = recipe.getProcessingDuration();
        var current = Math.max(total - millstone.timer, 0);

        if (total <= 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Instance of '%s' has a recipe with a processing duration less than 1.",
                            MillstoneBlockEntity.class
                    )
            );
        }

        var percent = Math.clamp((double) current / total, 0.0, 1.0);

        return ProgressBarTooltip.getColoredBar(8, percent);
    }

    @Nullable
    private static MutableComponent getCrushingWheelTooltip(CrushingWheelBlockEntity crushingWheel) {
        var controller = StructureFinder.getCrushingWheelController(crushingWheel);

        if (controller == null)
            return null;

        if (!controller.isOccupied())
            return null;

        var recipe = RecipeFinder.getCrushingRecipe(controller);

        if (recipe == null) {
            CreateInsights.LOGGER.debug("Failed to get the recipe of the crushing wheel.");
            return null;
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

        return ProgressBarTooltip.getColoredBar(8, percent);
    }
}
