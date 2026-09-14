package dev.warpersan.create_insights.network;

import dev.warpersan.create_insights.CreateInsights;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record RequestDataPayload(BlockPos pos, String className, String fieldName) implements CustomPacketPayload
{
	public static final Type<RequestDataPayload> TYPE = new Type<>(
			ResourceLocation.fromNamespaceAndPath(CreateInsights.MOD_ID, "request_data_payload")
	);

	public static final StreamCodec<FriendlyByteBuf, RequestDataPayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, RequestDataPayload::pos,
			ByteBufCodecs.STRING_UTF8, RequestDataPayload::className,
			ByteBufCodecs.STRING_UTF8, RequestDataPayload::fieldName,
			RequestDataPayload::new
	);

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type()
	{
		return TYPE;
	}
}
