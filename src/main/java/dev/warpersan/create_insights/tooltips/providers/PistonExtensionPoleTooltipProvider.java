package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.piston.MechanicalPistonBlock;
import com.simibubi.create.content.contraptions.piston.PistonExtensionPoleBlock;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.ITooltipProvider;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Provider responsible to display the length of piston extension poles
 */
public class PistonExtensionPoleTooltipProvider implements ITooltipProvider
{
	@Override
	public boolean provide(GoggleTooltipCollector.TooltipContext context)
	{
		if (!context.isWearingGoggles())
			return true;

		var level = context.level();
		var pos = context.pos();
		var state = level.getBlockState(pos);

		if (!AllBlocks.PISTON_EXTENSION_POLE.has(state))
			return true;

		var axis = state.getValue(PistonExtensionPoleBlock.FACING).getAxis();

		var poleCount = getPoleCount(level, pos, axis);

		if (poleCount == null)
			return true;

		if (context.hasTooltip())
			context.add(CommonComponents.EMPTY);

		var tooltip = new ArrayList<Component>();

		CreateLang.translate("gui.goggles.pole_length")
				.text(" " + poleCount)
				.forGoggles(tooltip);

		context.addAll(tooltip);

		return true;
	}

	/**
	 * Gets the amount of poles connected in the given axe
	 */
	@Nullable
	private static Integer getPoleCount(Level level, BlockPos pos, Direction.Axis axis)
	{
		var directions = Iterate.directionsInAxis(axis);

		var poles = 1;
		var pistonFound = false;

		for (var dir : directions)
		{
			var attachedPoles = PistonExtensionPoleBlock.PlacementHelper.get()
					.attachedPoles(level, pos, dir);

			poles += attachedPoles;

			var nextState = level.getBlockState(pos.relative(dir, attachedPoles + 1));

			pistonFound |= nextState.getBlock() instanceof MechanicalPistonBlock;
		}

		if (!pistonFound)
			return null;

		return poles;
	}
}
