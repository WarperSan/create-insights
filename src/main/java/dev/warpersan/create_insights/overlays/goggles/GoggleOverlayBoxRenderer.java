package dev.warpersan.create_insights.overlays.goggles;

import com.simibubi.create.foundation.gui.RemovedGuiUtils;
import net.createmod.catnip.gui.element.BoxElement;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2i;

import java.util.List;

/**
 * Draws the goggle overlay's icon and tooltip box: fade-in, positioning, theme colors, and the
 * ModernUI cursor-jiggle workaround.
 */
final class GoggleOverlayBoxRenderer
{
	/**
	 * Structure holding information about the colors of the tooltip
	 */
	private record TooltipColors(Color background, Color borderTop, Color borderBottom)
	{
	}

	public static void render(
			GuiGraphics graphics,
			ItemStack icon,
			List<Component> tooltip
	)
	{
		var minecraft = Minecraft.getInstance();
		var font = minecraft.font;

		var width = graphics.guiWidth();
		var height = graphics.guiHeight();

		var position = computePosition(font, tooltip, width, height);
		var colors = resolveColors();

		GuiGameElement.of(icon).at(position.x() + 10, position.y() - 16, 450).render(graphics);

		RemovedGuiUtils.drawHoveringText(
				graphics,
				tooltip,
				position.x,
				position.y,
				width,
				height,
				-1,
				colors.background().getRGB(),
				colors.borderTop().getRGB(),
				colors.borderBottom().getRGB(),
				font
		);
	}

	/**
	 * Computes the target position of the given tooltip
	 */
	private static Vector2i computePosition(Font font, List<Component> tooltip, int width, int height)
	{
		var textWidth = 0;

		for (FormattedText line : tooltip)
			textWidth = Math.max(textWidth, font.width(line));

		var tooltipHeight = 8;

		// gap between title lines and next lines
		if (tooltip.size() > 1)
			tooltipHeight += 2 + (tooltip.size() - 1) * 10;

		var x = Math.min(width / 2, width - textWidth - 20);
		var y = Math.min(height / 2, height - tooltipHeight - 20);

		return new Vector2i(x, y);
	}

	/**
	 * Resolves the colors to use for the tooltip
	 */
	private static TooltipColors resolveColors()
	{
		var background = BoxElement.COLOR_VANILLA_BACKGROUND.scaleAlpha(.75f);
		var borderTop = BoxElement.COLOR_VANILLA_BORDER.getFirst().copy();
		var borderBottom = BoxElement.COLOR_VANILLA_BORDER.getSecond().copy();

		return new TooltipColors(background, borderTop, borderBottom);
	}
}
