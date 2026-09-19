package dev.warpersan.create_insights;

import com.mojang.logging.LogUtils;
import dev.warpersan.create_insights.events.ClientEvents;
import dev.warpersan.create_insights.events.ServerEvents;
import dev.warpersan.create_insights.overlays.goggles.GoggleOverlayRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.slf4j.Logger;

@Mod(CreateInsights.MOD_ID)
public class CreateInsights
{
	public static final String MOD_ID = "create_insights";
	public static final Logger LOGGER = LogUtils.getLogger();

	public CreateInsights(IEventBus modEventBus)
	{
		modEventBus.register(ClientEvents.class);
		modEventBus.register(ServerEvents.class);
	}
}
