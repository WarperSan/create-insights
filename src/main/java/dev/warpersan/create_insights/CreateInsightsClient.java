package dev.warpersan.create_insights;

import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.api.registries.TooltipCategoryRegistry;
import dev.warpersan.create_insights.network.ClientDataCache;
import dev.warpersan.create_insights.tooltips.providers.create.DisplayAssemblyExceptionsTooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.create.HaveGoggleInformationTooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.create.HaveHoveringInformationTooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.create.PistonExtensionPoleTooltipProvider;
import dev.warpersan.create_insights.tooltips.providers.insights.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = CreateInsights.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(Dist.CLIENT)
public class CreateInsightsClient
{
	public CreateInsightsClient(ModContainer container)
	{
		var eventBus = container.getEventBus();
		
		if (eventBus != null)
		{
			TooltipCategoryRegistry.CATEGORIES.register(eventBus);
		}

		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}

	@SubscribeEvent
	private static void onClientSetup(FMLClientSetupEvent event)
	{
		// Create
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, HaveGoggleInformationTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, HaveHoveringInformationTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, DisplayAssemblyExceptionsTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, PistonExtensionPoleTooltipProvider::new);

		// Custom
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, BlazeBurnerTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, CrushingWheelTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, DepotTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, EncasedFanTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, MillstoneTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PulseExtenderTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PulseTimerTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PulseRepeaterTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, DeployerTooltipProvider::new);
		GoggleTooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PumpTooltipProvider::new);
	}

	@SubscribeEvent
	private static void onLeave(ClientPlayerNetworkEvent.LoggingOut event)
	{
		var player = event.getPlayer();

		if (player == null)
			return;

		if (!player.isLocalPlayer())
			return;

		ClientDataCache.clear();
	}
}
