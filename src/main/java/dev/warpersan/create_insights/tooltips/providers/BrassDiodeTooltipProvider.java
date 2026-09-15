package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.redstone.diodes.PulseRepeaterBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import net.minecraft.network.chat.Component;

import java.util.List;

public class PulseRepeaterTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var pulseRepeater = context.getBlockEntity(PulseRepeaterBlockEntity.class);

		if (pulseRepeater == null)
			return true;

		CreateLang.text(String.valueOf(pulseRepeater.getProgress())).addTo(tooltip);
		return true;
	}
}
