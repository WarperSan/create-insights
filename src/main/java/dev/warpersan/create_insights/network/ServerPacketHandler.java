package dev.warpersan.create_insights.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerPacketHandler
{
	// Caches the accessible Field objects across all classes
	private static final Map<String, Field> FIELD_CACHE = new ConcurrentHashMap<>();

	public static void handleRequest(RequestDataPayload payload, IPayloadContext context)
	{
		context.enqueueWork(() ->
		{
			var be = context.player().level().getBlockEntity(payload.pos());
			if (be == null) return;

			var cacheKey = payload.className() + "#" + payload.fieldName();
			try
			{
				var field = FIELD_CACHE.computeIfAbsent(cacheKey, key ->
				{
					try
					{
						var clazz = Class.forName(payload.className());
						var f = clazz.getDeclaredField(payload.fieldName());
						f.setAccessible(true);
						return f;
					} catch (Exception e)
					{
						return null; // Field or Class doesn't exist
					}
				});

				if (field != null)
				{
					var rawValue = field.get(be);
					var stringValue = rawValue != null ? rawValue.toString() : "null";

					// Reply automatically with the generic data payload
					context.reply(new ResponseDataPayload(payload.pos(), payload.fieldName(), stringValue));
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
