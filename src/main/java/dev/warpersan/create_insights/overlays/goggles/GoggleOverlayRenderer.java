package dev.warpersan.create_insights.overlays.goggles;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBox;
import dev.warpersan.create_insights.api.GoggleTooltipCollector;
import net.createmod.catnip.outliner.Outliner;
import net.createmod.catnip.outliner.Outliner.OutlineEntry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * Class responsible to manage the overlay lifespan
 */
public class GoggleOverlayRenderer implements LayeredDraw.Layer
{
	public static final LayeredDraw.Layer OVERLAY = new GoggleOverlayRenderer();

	private static final Map<Object, OutlineEntry> outlines = Outliner.getInstance().getOutlines();

	private static int hoverTicks = 0;

	private GoggleOverlayRenderer()
	{
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker)
	{
		var minecraft = Minecraft.getInstance();

		if (!isEnabled(minecraft))
			return;

		if (!(minecraft.hitResult instanceof BlockHitResult result))
			return;

		if (isOverlayBlocked())
			return;

		var world = minecraft.level;
		var player = minecraft.player;

		if (world == null || player == null)
			return;

		var pos = result.getBlockPos();

		var tooltip = GoggleTooltipCollector.collect(world, player, pos);

		if (tooltip.isEmpty())
		{
			hoverTicks = 0;
			return;
		}

		var icon = AllItems.GOGGLES.asStack();

		var poseStack = guiGraphics.pose();
		poseStack.pushPose();

		hoverTicks++;
		var fade = Mth.clamp(
				(hoverTicks + deltaTracker.getGameTimeDeltaPartialTick(false)) / 24f,
				0,
				1
		);

		if (fade < 1)
			poseStack.translate(Math.pow(1 - fade, 3) * Math.signum(0.5f) * 8, 0, 0);

		GoggleOverlayBoxRenderer.render(
				guiGraphics,
				icon,
				tooltip
		);

		poseStack.popPose();
	}

	/**
	 * Defines if the overlay is enabled
	 */
	private static boolean isEnabled(Minecraft minecraft)
	{
		if (minecraft.options.hideGui)
			return false;

		//noinspection RedundantIfStatement
		if (minecraft.gameMode == null || minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR)
			return false;

		return true;
	}

	/**
	 * Defines if the overlay is blocked by another visual element
	 */
	private boolean isOverlayBlocked()
	{
		for (var entry : outlines.values())
		{
			if (!entry.isAlive())
				continue;

			var outline = entry.getOutline();

			if (outline instanceof ValueBox valueBox && !valueBox.isPassive)
				return true;
		}

		return false;
	}
}
