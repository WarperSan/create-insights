package dev.warpersan.create_insights.events;

import dev.warpersan.create_insights.network.RequestDataPayload;
import dev.warpersan.create_insights.network.ServerPacketHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

/**
 * Class holding events ran on the server side
 */
@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerEvents
{
	@SubscribeEvent
	public static void registerPayloadEvents(RegisterPayloadHandlersEvent event)
	{
		var registrar = event.registrar("1");

		registrar.playToServer(
				RequestDataPayload.TYPE,
				RequestDataPayload.CODEC,
				ServerPacketHandler::handleRequest
		);
	}
}
