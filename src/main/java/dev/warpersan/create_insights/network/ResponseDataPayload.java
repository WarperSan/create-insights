package dev.warpersan.create_insights.network;

import dev.warpersan.create_insights.CreateInsights;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ResponseDataPayload(ResourceLocation dimension, BlockPos pos, String fieldName, String value) implements CustomPacketPayload
{
	public static final Type<ResponseDataPayload> TYPE = new Type<>(
			ResourceLocation.fromNamespaceAndPath(CreateInsights.MOD_ID, "response_data_payload")
	);

	public static final StreamCodec<FriendlyByteBuf, ResponseDataPayload> CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, ResponseDataPayload::dimension,
			BlockPos.STREAM_CODEC, ResponseDataPayload::pos,
			ByteBufCodecs.STRING_UTF8, ResponseDataPayload::fieldName,
			ByteBufCodecs.STRING_UTF8, ResponseDataPayload::value,
			ResponseDataPayload::new
	);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type()
	{
		return TYPE;
	}
}
