package dev.warpersan.create_insights.recipes.accessors;

import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import net.neoforged.fml.util.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class MillstoneBlockEntityAccessor {
    private static final Field LAST_RECIPE_FIELD = ObfuscationReflectionHelper.findField(MillstoneBlockEntity.class, "lastRecipe");

    /**
     * Gets the last recipe of the given millstone
     */
    @Nullable
    public static MillingRecipe getLastRecipe(MillstoneBlockEntity millstone) {
        try {
            var raw = LAST_RECIPE_FIELD.get(millstone);

            if (raw instanceof MillingRecipe recipe)
                return recipe;

            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access lastRecipe field", e);
        }
    }
}
