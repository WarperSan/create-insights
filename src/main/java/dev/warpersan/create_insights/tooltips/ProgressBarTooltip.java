package dev.warpersan.create_insights.tooltips;

import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

/**
 * Class used to help with progress bars in tooltips
 */
public class ProgressBarTooltip {

    /**
     * Gets a component representing a colored progress bar of the given width
     * @param percent percentage of the progress from 0 to 1
     */
    public static MutableComponent getColoredBar(int width, double percent) {
        percent = Math.clamp(percent, 0.0, 1.0);

        var builder = CreateLang.builder();
        
        var bar = TooltipHelper.makeProgressBar(
                width,
                (int) (width * percent)
        );

        builder.add(CreateLang.text(bar));
        builder.text(" ");
        builder.text(String.format("%3.0f%%", percent * 100));

        if (percent <= 0.25)
            builder.style(ChatFormatting.DARK_RED);
        else if (percent <= 0.50)
            builder.style(ChatFormatting.RED);
        else if (percent <= 0.75)
            builder.style(ChatFormatting.GOLD);
        else if (percent < 1)
            builder.style(ChatFormatting.GREEN);
        else
            builder.style(ChatFormatting.DARK_GREEN);

        return builder.component();
    }
}
