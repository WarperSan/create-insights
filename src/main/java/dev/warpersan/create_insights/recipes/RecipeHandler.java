package dev.warpersan.create_insights.recipes;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to create a recipe tooltip for blocks
 */
public class RecipeHandler {

    /**
     * Gets the recipe tooltip of the given block
     */
    public static List<Component> getRecipeTooltip(BlockEntity blockEntity) {

        if (blockEntity instanceof MillstoneBlockEntity millstone) {
            var list = new ArrayList<Component>();

            CreateLang.builder().text(String.valueOf(millstone.timer))
                    .addTo(list);

            return list;
        }

        return new ArrayList<>();
    }
}
