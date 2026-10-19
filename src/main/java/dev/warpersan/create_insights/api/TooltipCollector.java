package dev.warpersan.create_insights.api;

import dev.warpersan.create_insights.api.interfaces.ITooltipCategory;
import dev.warpersan.create_insights.api.interfaces.ITooltipProvider;
import dev.warpersan.create_insights.api.registries.TooltipCategoryRegistry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.*;
import java.util.function.Supplier;

/**
 * Class responsible to collect tooltips
 */
public final class TooltipCollector
{
	private static final Comparator<ITooltipCategory> CATEGORY_ORDER = Comparator
			.comparingInt(ITooltipCategory::priority)
			.thenComparing(TooltipCategoryRegistry.REGISTRY::getId);

	private static final SortedMap<ITooltipCategory, List<ITooltipProvider>> Categories = new TreeMap<>(CATEGORY_ORDER);

	/**
	 * Adds the given provider into the given category
	 */
	public static void addProvider(
			Supplier<ITooltipCategory> category,
			Supplier<ITooltipProvider> provider
	)
	{
		var providers = Categories.computeIfAbsent(category.get(), c -> new ArrayList<>());

		providers.add(provider.get());
	}

	/**
	 * Collects the tooltips shown for the given context
	 */
	public static List<Component> collect(TooltipContext context)
	{
		var components = new ArrayList<Component>();

		for (var entry : Categories.entrySet())
		{
			var category = entry.getKey();
			var providers = entry.getValue();
			var categoryComponents = new ArrayList<Component>();

			for (var provider : providers)
			{
				var providerComponents = provider.provide(context);

				if (providerComponents.isEmpty())
					continue;

				if (!categoryComponents.isEmpty())
					categoryComponents.add(CommonComponents.EMPTY);

				categoryComponents.addAll(providerComponents);
			}

			if (categoryComponents.isEmpty())
				continue;

			if (!components.isEmpty())
				components.add(CommonComponents.EMPTY);

			components.addAll(category.header());
			components.addAll(categoryComponents);
		}

		return components;
	}
}