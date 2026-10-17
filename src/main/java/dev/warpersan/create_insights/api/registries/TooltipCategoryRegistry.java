package dev.warpersan.create_insights.api.registries;

import com.simibubi.create.Create;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.api.interfaces.ITooltipCategory;
import dev.warpersan.create_insights.tooltips.categories.CreateTooltipCategory;
import dev.warpersan.create_insights.tooltips.categories.InsightsTooltipCategory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class TooltipCategoryRegistry
{
	/// Key of the registry
	private static final ResourceKey<Registry<ITooltipCategory>> REGISTRY_KEY = ResourceKey.createRegistryKey(
			ResourceLocation.fromNamespaceAndPath(
					CreateInsights.MOD_ID,
					"tooltip_category"
			)
	);

	/// Registry containing all tooltip categories
	public static final Registry<ITooltipCategory> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY).create();

	/// Registry containing all categories added by this mod
	public static final DeferredRegister<ITooltipCategory> CATEGORIES = DeferredRegister.create(REGISTRY, CreateInsights.MOD_ID);

	/// Category used for tooltips from Create
	public static final Supplier<ITooltipCategory> CREATE = CATEGORIES.register(Create.ID, CreateTooltipCategory::new);

	/// Category used for tooltips from Create: Insights
	public static final Supplier<ITooltipCategory> INSIGHTS = CATEGORIES.register(CreateInsights.MOD_ID, InsightsTooltipCategory::new);
}
