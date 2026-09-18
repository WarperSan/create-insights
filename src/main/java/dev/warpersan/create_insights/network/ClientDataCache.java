package dev.warpersan.create_insights.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class responsible to request server-only data about blocks
 */
public class ClientDataCache
{
	private static final Map<String, CacheEntry> CLIENT_CACHE = new ConcurrentHashMap<>();
	private static final long CACHE_EXPIRY_MS = 200;

	/**
	 * Class holding information about a cached value
	 */
	@SuppressWarnings("ClassCanBeRecord")
	private static class CacheEntry
	{
		private final String value;
		private final long timestamp;
		private final boolean isTemporary;

		/**
		 * Creates an empty temporary entry
		 */
		public CacheEntry()
		{
			this.value = null;
			this.timestamp = 0;
			this.isTemporary = true;
		}

		/**
		 * Creates an entry of the given value
		 */
		public CacheEntry(String value)
		{
			this.value = value;
			this.timestamp = System.currentTimeMillis();
			this.isTemporary = false;
		}

		/**
		 * Gets the cached value of this entry
		 */
		public String value()
		{
			return value;
		}

		/**
		 * Checks if this entry is up to date
		 */
		public boolean isUpToDate()
		{
			if (isTemporary)
				return true;

			var now = System.currentTimeMillis();
			var timeSinceLastUpdate = now - timestamp;

			return timeSinceLastUpdate <= CACHE_EXPIRY_MS;
		}
	}

	/**
	 * Computes the key of the given field at the given position
	 */
	private static String getKey(ResourceLocation dimension, BlockPos pos, String fieldName)
	{
		return dimension + "#" + pos.asLong() + "#" + fieldName;
	}

	/**
	 * Gets the entry of the given field at the given position
	 */
	@Nullable
	private static CacheEntry get(ResourceLocation dimension, BlockPos pos, String fieldName)
	{
		var cacheKey = getKey(dimension, pos, fieldName);

		return CLIENT_CACHE.getOrDefault(cacheKey, null);
	}

	/**
	 * Sets the given entry to the given field at the given position
	 */
	private static void set(ResourceLocation dimension, BlockPos pos, String fieldName, CacheEntry entry)
	{
		var cacheKey = getKey(dimension, pos, fieldName);

		CLIENT_CACHE.put(cacheKey, entry);
	}

	/**
	 * Sets the given entry to the given field at the given position if no other entry is present
	 */
	private static void setIfAbsent(ResourceLocation dimension, BlockPos pos, String fieldName, CacheEntry entry)
	{
		var cacheKey = getKey(dimension, pos, fieldName);

		CLIENT_CACHE.putIfAbsent(cacheKey, entry);
	}

	/**
	 * Gets the value of the given field, or requests it for future calls
	 */
	@Nullable
	public static String getOrRequest(
			BlockEntity blockEntity,
			Class<?> targetClass,
			String fieldName
	)
	{
		var level = blockEntity.getLevel();

		if (level == null)
			return null;

		var dimension = level.dimension();
		
		return getOrRequest(
				dimension.location(),
				blockEntity.getBlockPos(),
				targetClass,
				fieldName
		);
	}

	/**
	 * Gets the value of the given field, or requests it for future calls
	 */
	@Nullable
	public static String getOrRequest(
			ResourceLocation dimension,
			BlockPos pos,
			Class<?> targetClass,
			String fieldName
	)
	{
		var entry = get(dimension, pos, fieldName);

		if (entry == null || !entry.isUpToDate())
			request(dimension, pos, targetClass, fieldName);

		if (entry != null)
			return entry.value();

		return null;
	}

	/**
	 * Requests the given field to be updated
	 */
	private static void request(
			ResourceLocation dimension,
			BlockPos pos,
			Class<?> targetClass,
			String fieldName
	)
	{
		var data = new RequestDataPayload(
				dimension,
				pos,
				targetClass.getName(),
				fieldName
		);

		PacketDistributor.sendToServer(data);

		var entry = new CacheEntry();

		setIfAbsent(dimension, pos, fieldName, entry);
	}

	/**
	 * Clears all cache
	 */
	public static void clear()
	{
		CLIENT_CACHE.clear();
	}

	/**
	 * Handles the response of the update
	 */
	public static void handleResponse(ResponseDataPayload payload)
	{
		var dimension = payload.dimension();
		var pos = payload.pos();
		var fieldName = payload.fieldName();
		var value = payload.value();

		var entry = new CacheEntry(value);

		set(dimension, pos, fieldName, entry);
	}
}
