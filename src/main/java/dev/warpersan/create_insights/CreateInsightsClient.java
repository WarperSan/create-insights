package dev.warpersan.create_insights;

import dev.warpersan.create_insights.api.TooltipCollector;
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
		TooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, HaveGoggleInformationTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, HaveHoveringInformationTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, DisplayAssemblyExceptionsTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.CREATE, PistonExtensionPoleTooltipProvider::new);

		// Custom
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, BlazeBurnerTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, CrushingWheelTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, DepotTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, EncasedFanTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, MillstoneTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PulseExtenderTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PulseTimerTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PulseRepeaterTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, DeployerTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, PumpTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, BrassTunnelTooltipProvider::new);
		TooltipCollector.addProvider(TooltipCategoryRegistry.INSIGHTS, MechanicalArmTooltipProvider::new);
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
