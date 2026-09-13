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
}
