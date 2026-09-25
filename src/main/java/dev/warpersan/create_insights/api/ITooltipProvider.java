package dev.warpersan.create_insights.api;

/**
 * Interface that represents every class that can provide a tooltip
 */
public interface ITooltipProvider
{
	/**
	 * Provides the tooltips depending on the given context
	 */
	void provide(GoggleTooltipCollector.TooltipContext context);
}
