package dev.warpersan.create_insights.api.interfaces;

import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Interface that represents every class that can organize providers
 */
public interface ITooltipCategory
{
	/**
	 * Gets the priority of the category
	 * @apiNote The lowest number is the first
	 */
	int priority();

	/**
	 * Gets the header of the category
	 */
	List<Component> header();
}
