package dev.warpersan.create_insights.events;

import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.network.ClientDataCache;
import dev.warpersan.create_insights.network.ResponseDataPayload;
import dev.warpersan.create_insights.overlays.goggles.GoggleOverlayRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

/**
 * Class holding events ran on the client side
 */
@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents
{
	@SubscribeEvent
	private static void registerPayloadEvents(RegisterPayloadHandlersEvent event)
	{
		var registrar = event.registrar("1");

		registrar.playToClient(
				ResponseDataPayload.TYPE,
				ResponseDataPayload.CODEC,
				(payload, context) -> ClientDataCache.handleResponse(payload)
		);
	}

	@SubscribeEvent
	private static void registerGuiEvents(RegisterGuiLayersEvent event)
	{
		event.registerAbove(
				VanillaGuiLayers.HOTBAR,
				ResourceLocation.fromNamespaceAndPath(CreateInsights.MOD_ID, "goggle_info"),
				GoggleOverlayRenderer.OVERLAY
		);
	}
}
