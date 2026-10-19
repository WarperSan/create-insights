package dev.warpersan.create_insights.tooltips.categories;

import dev.warpersan.create_insights.api.interfaces.ITooltipCategory;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Category used to group providers for Create
 */
public class CreateTooltipCategory implements ITooltipCategory
{
	@Override
	public int priority()
	{
		return 100;
	}

	@Override
	public List<Component> header()
	{
		// Create handles its own headers
		return List.of();
	}
}
