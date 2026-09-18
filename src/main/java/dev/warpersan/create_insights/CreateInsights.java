package dev.warpersan.create_insights;

import com.mojang.logging.LogUtils;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import dev.warpersan.create_insights.events.ClientEvents;
import dev.warpersan.create_insights.events.ServerEvents;
import dev.warpersan.create_insights.overlays.goggles.GoggleOverlayRenderer;
import dev.warpersan.create_insights.tooltips.providers.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.slf4j.Logger;

@Mod(CreateInsights.MOD_ID)
public class CreateInsights
{
	public static final String MOD_ID = "create_insights";
	public static final Logger LOGGER = LogUtils.getLogger();

	public CreateInsights(IEventBus modEventBus, ModContainer modContainer)
	{
		//NeoForge.EVENT_BUS.register(this);
		modEventBus.register(ClientEvents.class);
		modEventBus.register(ServerEvents.class);
		modEventBus.register(this);

		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

		GoggleTooltipCollector.addProvider(new HaveGoggleInformationTooltipProvider());
		GoggleTooltipCollector.addProvider(new BlazeBurnerTooltipProvider());
		GoggleTooltipCollector.addProvider(new PulseRepeaterTooltipProvider());
		GoggleTooltipCollector.addProvider(new PulseTimerTooltipProvider());
		GoggleTooltipCollector.addProvider(new PulseExtenderTooltipProvider());
		GoggleTooltipCollector.addProvider(new MillstoneTooltipProvider());
	}

	@SubscribeEvent
	private void registerGuiEvents(RegisterGuiLayersEvent event)
	{
		event.registerAbove(
				VanillaGuiLayers.HOTBAR,
				ResourceLocation.fromNamespaceAndPath(CreateInsights.MOD_ID, "goggle_info"),
				GoggleOverlayRenderer.OVERLAY
		);
	}
}
