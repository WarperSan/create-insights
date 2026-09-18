package dev.warpersan.create_insights;

import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.network.ClientDataCache;
import dev.warpersan.create_insights.tooltips.providers.*;
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
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}

	@SubscribeEvent
	private static void onClientSetup(FMLClientSetupEvent event)
	{
		GoggleTooltipCollector.addProvider(new HaveGoggleInformationTooltipProvider());
		GoggleTooltipCollector.addProvider(new BlazeBurnerTooltipProvider());
		GoggleTooltipCollector.addProvider(new PulseRepeaterTooltipProvider());
		GoggleTooltipCollector.addProvider(new PulseTimerTooltipProvider());
		GoggleTooltipCollector.addProvider(new PulseExtenderTooltipProvider());
		GoggleTooltipCollector.addProvider(new MillstoneTooltipProvider());
		GoggleTooltipCollector.addProvider(new EncasedFanTooltipProvider());
		GoggleTooltipCollector.addProvider(new CrushingWheelTooltipProvider());
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
