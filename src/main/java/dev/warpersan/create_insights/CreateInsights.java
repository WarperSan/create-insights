package dev.warpersan.create_insights;

import com.mojang.logging.LogUtils;
import dev.warpersan.create_insights.events.ClientEvents;
import dev.warpersan.create_insights.events.ServerEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
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
