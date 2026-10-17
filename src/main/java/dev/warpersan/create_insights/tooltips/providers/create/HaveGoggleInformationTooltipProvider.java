package dev.warpersan.create_insights.tooltips.providers.create;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.api.interfaces.ITooltipProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider responsible to handle {@link IHaveGoggleInformation}
 */
public class HaveGoggleInformationTooltipProvider implements ITooltipProvider
{
	@Override
	public List<Component> provide(TooltipContext context)
	{
		if (!context.isWearingGoggles())
			return List.of();

		var blockEntity = context.getBlockEntity(IHaveGoggleInformation.class);

		if (blockEntity == null)
			return List.of();

		var tooltip = new ArrayList<Component>();

		blockEntity.addToGoggleTooltip(tooltip, context.player().isCrouching());

		return tooltip;
	}
}
