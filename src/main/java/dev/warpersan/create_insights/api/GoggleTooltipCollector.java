package dev.warpersan.create_insights.api;

import com.simibubi.create.content.equipment.goggles.GogglesItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class GoggleTooltipCollector
{
	private static final List<ITooltipProvider> Providers = new ArrayList<>();

	/**
	 * Class holding information about the creation of tooltips
	 */
	public static class TooltipContext
	{
		private final List<Component> tooltip;

		private final Level level;
		private final Player player;
		private final BlockPos pos;

		private TooltipContext(Level level, Player player, BlockPos pos)
		{
			tooltip = new ArrayList<>();

			this.level = level;
			this.player = player;
			this.pos = pos;
		}

		/**
		 * Gets the player
		 */
		public Player player()
		{
			return player;
		}

		/**
		 * Defines if the player is wearing goggles
		 */
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

		/**
		 * Defines if any tooltip was defined
		 */
		public boolean hasTooltip()
		{
			return !tooltip.isEmpty();
		}

		/**
		 * Adds the given component to the tooltip
		 */
		public void add(Component component)
		{
			tooltip.add(component);
		}

		/**
		 * Adds the given components to the tooltip
		 */
		public void addAll(Collection<? extends Component> components)
		{
			for (var component : components)
				add(component);
		}

		private List<Component> compile()
		{
			return new ArrayList<>(tooltip);
		}
	}

	/**
	 * Adds the given provider
	 */
	public static void addProvider(ITooltipProvider provider)
	{
		Providers.add(provider);
	}

	/**
	 * Collects the tooltips shown at the given position
	 */
	public static List<Component> collect(Level level, Player player, BlockPos pos)
	{
		var context = new TooltipContext(level, player, pos);

		for (var provider : Providers)
		{
			var shouldContinue = provider.provide(context);

			if (!shouldContinue)
				break;
		}

		return context.compile();
	}
}