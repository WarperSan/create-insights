package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import dev.warpersan.create_insights.api.TooltipContext;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * Provider responsible to display the distribution method on the given mechanical arms
 */
public class MechanicalArmTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected void onProvide(TooltipContext context, List<Component> tooltip)
	{
		var arm = context.getBlockEntity(ArmBlockEntity.class);

		if (arm == null)
			return;

		var scrollValueBehaviour = arm.getBehaviour(ScrollOptionBehaviour.TYPE);

		if (scrollValueBehaviour == null)
			return;

		if (!(scrollValueBehaviour instanceof ScrollOptionBehaviour<?> scrollOptionBehavior))
			return;

		var rawValue = scrollOptionBehavior.get();

		if (!(rawValue instanceof ArmBlockEntity.SelectionMode value))
			return;

		var headerBuilder = new InsightsBuilder()
				.translate("tooltip.usage")
				.header();

		headerBuilder.addTo(tooltip);

		var builder = new InsightsBuilder();

		builder.translate("tooltip.usage.mode");
		builder.space();
		builder.translatable(ChatFormatting.GOLD, value.getTranslationKey());
		builder.style(ChatFormatting.DARK_GRAY);
		builder.indentInto(tooltip);
	}
}
