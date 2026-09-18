package dev.warpersan.create_insights.events;

import dev.warpersan.create_insights.network.ClientDataCache;
import dev.warpersan.create_insights.network.ResponseDataPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
}
