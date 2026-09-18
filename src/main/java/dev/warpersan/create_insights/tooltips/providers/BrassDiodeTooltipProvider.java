package dev.warpersan.create_insights.tooltips.providers;

import com.simibubi.create.content.redstone.diodes.BrassDiodeBlockEntity;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.network.ClientDataCache;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Provider responsible to offer methods for brass diode block entities
 */
public abstract class BrassDiodeTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected boolean onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var brassDiode = context.getBlockEntity(BrassDiodeBlockEntity.class);

		if (brassDiode == null)
			return true;

		return onProvide(brassDiode, context, tooltip);
	}

	/**
	 * Called when providing a brass diode block entity 
	 */
	protected abstract boolean onProvide(
			BrassDiodeBlockEntity blockEntity,
			GoggleTooltipCollector.TooltipContext context,
			List<Component> tooltip
	);

	/**
	 * Gets the max time in ticks of the given brass diode block entity
	 */
	@Nullable
	protected static Integer getMaxTime(BrassDiodeBlockEntity blockEntity)
	{
		var maxTimeValue = ClientDataCache.getOrRequest(
				blockEntity,
				BrassDiodeBlockEntity.class,
				"maxState.value"
		);

		if (maxTimeValue == null)
			return null;

		return Integer.parseInt(maxTimeValue);
	}
}
