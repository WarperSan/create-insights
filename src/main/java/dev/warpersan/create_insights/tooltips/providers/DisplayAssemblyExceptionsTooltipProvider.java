package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.contraptions.IDisplayAssemblyExceptions;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.ITooltipProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

/**
 * Provider responsible to display the assembly exceptions
 */
public class DisplayAssemblyExceptionsTooltipProvider implements ITooltipProvider
{
	@Override
	public boolean provide(GoggleTooltipCollector.TooltipContext context)
	{
		if (!context.isWearingGoggles())
			return true;

		var displayAssembly = context.getBlockEntity(IDisplayAssemblyExceptions.class);

		if (displayAssembly == null)
			return true;

		var tooltip = new ArrayList<Component>();

		displayAssembly.addExceptionToTooltip(tooltip);

		context.addAll(tooltip);
		return true;
	}
}
