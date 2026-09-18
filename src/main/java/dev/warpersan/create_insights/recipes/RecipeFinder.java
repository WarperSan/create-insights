package dev.warpersan.create_insights.recipes;

import com.simibubi.create.AllRecipeTypes;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Class responsible to find recipes
 */
public class RecipeFinder {

    /**
     * Gets the recipe of the given inventory
     */
    @Nullable
    private static Recipe<RecipeInput> getRecipe(AllRecipeTypes recipes, IItemHandler inventory, @NotNull Level level) {
        var wrapper = new RecipeWrapper(inventory);
        Optional<RecipeHolder<Recipe<RecipeInput>>> recipe = recipes.find(wrapper, level);

        return recipe.map(RecipeHolder::value).orElse(null);
    }

    /**
     * Gets the recipe of the given block entity
     */
    @Nullable
	public static Recipe<RecipeInput> getRecipe(BlockEntity blockEntity, AllRecipeTypes recipes, IItemHandler inventory) {
        var level = blockEntity.getLevel();

        if (level == null)
            return null;

        return getRecipe(recipes, inventory, level);
    }
}
