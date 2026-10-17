package dev.warpersan.create_insights.api;

import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Interface that represents every class that can provide a tooltip
 */
public interface ITooltipProvider
{
	/**
	 * Provides the tooltips depending on the given context
	 */
	List<Component> provide(TooltipContext context);
}
