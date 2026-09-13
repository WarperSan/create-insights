package dev.warpersan.create_insights.recipes;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.warpersan.create_insights.recipes.accessors.MillstoneBlockEntityAccessor;
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

        if (blockEntity instanceof MillstoneBlockEntity millstone)
            return getMillstoneTooltip(millstone);

        return null;
    }

    @Nullable
    private static MutableComponent getMillstoneTooltip(MillstoneBlockEntity millstone) {
        var recipe = MillstoneBlockEntityAccessor.getLastRecipe(millstone);

        if (recipe == null)
            return null;

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

        var builder = CreateLang.builder()
                .text(String.valueOf(millstone.timer));

        var barLength = 5;
        var bar = TooltipHelper.makeProgressBar(barLength, (int) (barLength * percent));

        builder.add(CreateLang.text(bar));

        return builder.component();
    }
}
