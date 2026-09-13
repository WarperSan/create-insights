package dev.warpersan.create_insights.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.warpersan.create_insights.foundation.item.RecipeStats;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = KineticBlockEntity.class, remap = false)
public class KineticBlockEntityMixin {
    @Inject(method = "addToGoggleTooltip", at = @At("TAIL"))
    private void afterAddToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {

        // Skip if failed
        if (!cir.getReturnValue())
            return;

        CreateLang.number(69420.0).forGoggles(tooltip);

        var stats = RecipeStats.getStats();

        tooltip.add(CommonComponents.EMPTY);
        tooltip.addAll(stats);
    }
}
