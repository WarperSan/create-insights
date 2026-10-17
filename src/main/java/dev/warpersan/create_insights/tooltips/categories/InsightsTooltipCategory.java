package dev.warpersan.create_insights.tooltips.categories;

import dev.warpersan.create_insights.api.interfaces.ITooltipCategory;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class InsightsTooltipCategory implements ITooltipCategory
{
	@Override
	public int priority()
	{
		return 200;
	}

	@Override
	public List<Component> header()
	{
		var header = new ArrayList<Component>();

		var builder = new InsightsBuilder().translate("gui.goggles.insights_stats");

		builder.forGoggles(header);

		return header;
	}
}
