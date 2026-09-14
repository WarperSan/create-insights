package dev.warpersan.create_insights.network;

import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientDataCache
{
	private static final Map<String, CacheEntry> CLIENT_CACHE = new ConcurrentHashMap<>();
	private static final long CACHE_EXPIRY_MS = 200; // 4 ticks buffer

	/**
	 * Class holding information about a cached value
	 */
	private record CacheEntry(String value, long timestamp)
	{
		/**
		 * Gets the cached value of this entry
		 */
		@Override
		public String value()
		{
			return value;
		}

		/**
		 * Checks if this entry is up to date
		 */
		public boolean isUpToDate()
		{
			var now = System.currentTimeMillis();
			var timeSinceLastUpdate = now - timestamp;

			return timeSinceLastUpdate <= CACHE_EXPIRY_MS;
		}
	}

	/**
	 * Computes the key of the given field at the given position
	 */
	private static String getKey(BlockPos pos, String fieldName)
	{
		return pos.asLong() + "#" + fieldName;
	}

	/**
	 * Gets the entry of the given field at the given position
	 */
	@Nullable
	private static CacheEntry get(BlockPos pos, String fieldName)
	{
		var cacheKey = getKey(pos, fieldName);

		return CLIENT_CACHE.getOrDefault(cacheKey, null);
	}

	/**
	 * Sets the entry of the given field at the given position to the given value
	 */
	private static void set(BlockPos pos, String fieldName, String value)
	{
		var entry = new CacheEntry(value, System.currentTimeMillis());
		var cacheKey = getKey(pos, fieldName);

		CLIENT_CACHE.put(cacheKey, entry);
	}

	/**
	 * Gets the value of the given field, or requests it for future calls
	 */
	@Nullable
	public static String getOrRequest(BlockPos pos, Class<?> targetClass, String fieldName)
	{
		var entry = get(pos, fieldName);

		if (entry == null || !entry.isUpToDate())
			request(pos, targetClass, fieldName);

		if (entry != null)
			return entry.value();

		return null;
	}

	/**
	 * Requests the given field to be updated
	 */
	private static void request(BlockPos pos, Class<?> targetClass, String fieldName)
	{
		PacketDistributor.sendToServer(
				new RequestDataPayload(pos, targetClass.getName(), fieldName)
		);
	}

	/**
	 * Handles the response of the update
	 */
	public static void handleResponse(ResponseDataPayload payload, IPayloadContext context)
	{
		var pos = payload.pos();
		var fieldName = payload.fieldName();
		var value = payload.value();

		set(pos, fieldName, value);
	}
}
