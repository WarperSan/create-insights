package dev.warpersan.create_insights.network;

import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.helpers.ReflectionHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;

/**
 * Class responsible to collect and distribute server-only data about blocks
 */
public class ServerDataCache
{
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
		
		var blockEntityLevel = blockEntity.getLevel();
		
		if (blockEntityLevel == null)
			return null;

		var value = ReflectionHelper.getValue(
				blockEntity,
				request.className(),
				fieldName
		);

		return new ResponseDataPayload(
				blockEntityLevel.dimension().location(),
				pos,
				fieldName,
				String.valueOf(value)
		);
	}
}
