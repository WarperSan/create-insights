package dev.warpersan.create_insights.network;

import dev.warpersan.create_insights.CreateInsights;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerPacketHandler
{
	private static final Map<String, Field> FIELD_CACHE = new ConcurrentHashMap<>();

	/**
	 * Handles the incoming request
	 */
	public static void handleRequest(RequestDataPayload payload, IPayloadContext context)
	{
		context.enqueueWork(() ->
		{
			CustomPacketPayload response;

			try
			{
				response = computeResponse(payload, context);
			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}

			if (response == null)
				return;

			context.reply(response);
		});
	}

	/**
	 * Computes a response to the given request in the given context
	 */
	@Nullable
	private static CustomPacketPayload computeResponse(RequestDataPayload request, IPayloadContext context)
	{
		var pos = request.pos();
		var fieldName = request.fieldName();

		BlockEntity blockEntity;

		//noinspection resource
		var level = context.player().level();

		try
		{
			blockEntity = level.getBlockEntity(pos);
		} catch (Exception e)
		{
			CreateInsights.LOGGER.error("Error while getting block entity.", e);
			return null;
		}

		if (blockEntity == null)
			return null;

		var value = getValue(
				blockEntity,
				request.className(),
				fieldName
		);

		return new ResponseDataPayload(
				pos,
				fieldName,
				String.valueOf(value)
		);
	}

	/**
	 * Gets the instance value of the given field
	 */
	@Nullable
	private static Object getValue(@NotNull Object object, @NotNull String className, @NotNull String fieldName)
	{
		var parts = fieldName.split("\\.", 2);
		var field = getField(className, parts[0]);

		if (field == null)
			return null;

		try
		{
			var value = field.get(object);
			
			if (parts.length == 1)
				return value;

			var subClassName = field.getType().getName();
			
			return getValue(value, subClassName, parts[1]);
		} catch (IllegalAccessException e)
		{
			return null;
		}
	}

	/**
	 * Gets the field in the given class with the given name
	 */
	@Nullable
	private static Field getField(String className, String fieldName)
	{
		var key = className + "#" + fieldName;

		if (FIELD_CACHE.containsKey(key))
			return FIELD_CACHE.get(key);

		try
		{
			var clazz = Class.forName(className);
			var field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			FIELD_CACHE.put(key, field);
			return field;
		} catch (ClassNotFoundException | NoSuchFieldException e)
		{
			return null;
		}
	}
}
