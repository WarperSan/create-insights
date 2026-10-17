package dev.warpersan.create_insights.api;

import com.simibubi.create.content.equipment.goggles.GogglesItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Context related to the tooltip collecting
 */
public record TooltipContext(Level level, Player player, BlockPos pos)
{
	/**
	 * Gets the level
	 */
	@Override
	public Level level()
	{
		return level;
	}

	/**
	 * Gets the player
	 */
	@Override
	public Player player()
	{
		return player;
	}

	/**
	 * Gets the position
	 */
	@Override
	public BlockPos pos()
	{
		return pos;
	}

	/**
	 * Defines if the player is wearing goggles
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isWearingGoggles()
	{
		return GogglesItem.isWearingGoggles(player);
	}

	/**
	 * Gets the targeted block entity
	 */
	@Nullable
	public <T> T getBlockEntity(Class<T> clazz)
	{
		var blockEntity = level.getBlockEntity(pos);

		if (blockEntity == null)
			return null;

		if (!clazz.isInstance(blockEntity))
			return null;

		return clazz.cast(blockEntity);
	}
}
