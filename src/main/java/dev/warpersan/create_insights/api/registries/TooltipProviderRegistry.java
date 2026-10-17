package dev.warpersan.create_insights.api.registries;

import com.simibubi.create.Create;
import dev.warpersan.create_insights.CreateInsights;
import dev.warpersan.create_insights.api.ITooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.create.DisplayAssemblyExceptionsTooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.create.HaveGoggleInformationTooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.create.HaveHoveringInformationTooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.create.PistonExtensionPoleTooltipProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class TooltipProviderRegistry
{
	/// Key of the registry
	private static final ResourceKey<Registry<ITooltipProvider>> TOOLTIP_PROVIDER_REGISTRY_KEY = ResourceKey.createRegistryKey(
			ResourceLocation.fromNamespaceAndPath(
					CreateInsights.MOD_ID,
					"tooltip_providers"
			)
	);

	public static final Registry<ITooltipProvider> TOOLTIP_PROVIDER_REGISTRY = new RegistryBuilder<>(TOOLTIP_PROVIDER_REGISTRY_KEY)
			.create();

	/// Registry specific for Create
	public static final DeferredRegister<ITooltipProvider> CREATE_PROVIDERS = DeferredRegister.create(TOOLTIP_PROVIDER_REGISTRY, Create.ID);

	public static final Supplier<ITooltipProvider> HAVE_HOVERING_INFORMATION_PROVIDER = CREATE_PROVIDERS.register("have_hovering_information", HaveHoveringInformationTooltipProvider::new);
	public static final Supplier<ITooltipProvider> HAVE_GOGGLE_INFORMATION_PROVIDER = CREATE_PROVIDERS.register("have_goggle_information", HaveGoggleInformationTooltipProvider::new);
	public static final Supplier<ITooltipProvider> PISTON_EXTENSION_POLE_PROVIDER = CREATE_PROVIDERS.register("piston_extension_pole", PistonExtensionPoleTooltipProvider::new);
	public static final Supplier<ITooltipProvider> DISPLAY_ASSEMBLY_EXCEPTIONS_PROVIDER = CREATE_PROVIDERS.register("display_assembly_exceptions", DisplayAssemblyExceptionsTooltipProvider::new);

	/// Registry specific for Create: Insights
	public static final DeferredRegister<ITooltipProvider> INSIGHTS_PROVIDERS = DeferredRegister.create(TOOLTIP_PROVIDER_REGISTRY, CreateInsights.MOD_ID);
}
