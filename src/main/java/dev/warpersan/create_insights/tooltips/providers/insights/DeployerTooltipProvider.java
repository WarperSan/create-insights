package dev.warpersan.create_insights.tooltips.providers.insights;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.helpers.ReflectionHelper;
import dev.warpersan.create_insights.tooltips.builders.InsightsBuilder;
import dev.warpersan.create_insights.tooltips.providers.InsightsTooltipProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Provider responsible to display the durability of the given deployer
 */
public class DeployerTooltipProvider extends InsightsTooltipProvider
{
	@Override
	protected void onProvide(GoggleTooltipCollector.TooltipContext context, List<Component> tooltip)
	{
		var deployer = context.getBlockEntity(DeployerBlockEntity.class);

		if (deployer == null)
			return;

		var item = ReflectionHelper.getValue(
				deployer,
				DeployerBlockEntity.class,
				"heldItem",
				ItemStack.class
		);

		if (item == null || item.isEmpty())
			return;

		var components = item.getComponents();
		var patchComponents = item.getComponentsPatch();

		var maxDamage = components.get(DataComponents.MAX_DAMAGE);
		var currentDamage = patchComponents.get(DataComponents.DAMAGE);

		if (maxDamage == null)
			return;

		var currentDamageValue = 0;

		if (currentDamage != null && currentDamage.isPresent())
			currentDamageValue = currentDamage.get();

		var remainingDurability = maxDamage - currentDamageValue;

		var headerBuilder = new InsightsBuilder()
				.translate("tooltip.usage")
				.header();

		headerBuilder.addTo(tooltip);

		var builder = new InsightsBuilder();

		builder.translate("tooltip.usage.durability");
		builder.add(Component.literal(" "));
		builder.add(Component.literal(String.valueOf(remainingDurability)).withStyle(ChatFormatting.DARK_AQUA));
		builder.add(Component.literal(" / "));
		builder.add(Component.literal(maxDamage.toString()));
		builder.style(ChatFormatting.DARK_GRAY);
		builder.indentInto(tooltip);
	}
}
