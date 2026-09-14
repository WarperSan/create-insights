package dev.warpersan.create_insights.api;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.content.redstone.diodes.PulseRepeaterBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
		 * Determines if the player is wearing the goggles
		 */
		public boolean isWearingGoggles()
		{
			return GogglesItem.isWearingGoggles(player);
		}

		/**
		 * Gets the targeted block entity
		 */
		@Nullable
		public BlockEntity getBlockEntity()
		{
			return level.getBlockEntity(pos);
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

		//collectGoggleInformation(tooltip, level, player, pos);

		return context.compile();
	}

	private static void collectGoggleInformation(List<Component> tooltip, Level level, Player player, BlockPos pos)
	{
		// Only display when wearing goggles
		if (!GogglesItem.isWearingGoggles(player))
			return;

		var blockEntity = level.getBlockEntity(pos);

		// Only handle block entities
		if (blockEntity == null)
			return;

		if (blockEntity instanceof IHaveGoggleInformation gte)
			gte.addToGoggleTooltip(tooltip, player.isCrouching());

		if (blockEntity instanceof PulseRepeaterBlockEntity pulseRepeater)
		{
			var builder = CreateLang.builder();
			builder.text("owo?");
			builder.add(CommonComponents.EMPTY);
			builder.text(String.valueOf(pulseRepeater.getBehaviour(ScrollValueBehaviour.TYPE).value));

			for (var key : pulseRepeater.getPersistentData().getAllKeys())
			{
				builder.text(key);
				builder.text(" = ");
				builder.text(Objects.requireNonNull(pulseRepeater.getPersistentData().get("key")).toString());
				builder.add(CommonComponents.EMPTY);
			}

			builder.forGoggles(tooltip);
		}
	}
}