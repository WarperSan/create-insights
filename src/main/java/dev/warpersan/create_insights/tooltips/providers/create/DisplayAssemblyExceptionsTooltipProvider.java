package dev.warpersan.create_insights.tooltips.providers.create;

import com.simibubi.create.content.contraptions.IDisplayAssemblyExceptions;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.api.interfaces.ITooltipProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider responsible to handle {@link IDisplayAssemblyExceptions}
 */
public class DisplayAssemblyExceptionsTooltipProvider implements ITooltipProvider
{
	@Override
	public List<Component> provide(TooltipContext context)
	{
		if (!context.isWearingGoggles())
			return List.of();

		var displayAssembly = context.getBlockEntity(IDisplayAssemblyExceptions.class);

		if (displayAssembly == null)
			return List.of();

		var tooltip = new ArrayList<Component>();

		displayAssembly.addExceptionToTooltip(tooltip);

		return tooltip;
	}
}
