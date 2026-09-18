package dev.warpersan.create_insights.mixin;

import com.simibubi.create.content.equipment.goggles.GoggleOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GoggleOverlayRenderer.class)
public class GoggleOverlayRendererMixin
{
	@Inject(
			method = "renderOverlay",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private static void cancelRender(CallbackInfo ci)
	{
		ci.cancel();
	}
}
