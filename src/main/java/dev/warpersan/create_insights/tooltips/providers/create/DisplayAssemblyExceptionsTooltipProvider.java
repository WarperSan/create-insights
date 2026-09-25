package dev.warpersan.create_insights.tooltips.providers.create;

import com.simibubi.create.content.contraptions.IDisplayAssemblyExceptions;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.ITooltipProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

/**
 * Provider responsible to handle {@link IDisplayAssemblyExceptions}
 */
public class DisplayAssemblyExceptionsTooltipProvider implements ITooltipProvider
{
	@Override
	public void provide(GoggleTooltipCollector.TooltipContext context)
	{
		if (!context.isWearingGoggles())
			return;

		var displayAssembly = context.getBlockEntity(IDisplayAssemblyExceptions.class);

		if (displayAssembly == null)
			return;

		var tooltip = new ArrayList<Component>();

		displayAssembly.addExceptionToTooltip(tooltip);

		context.addAll(tooltip);
	}
}
