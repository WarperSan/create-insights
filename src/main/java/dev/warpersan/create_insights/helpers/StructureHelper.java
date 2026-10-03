package dev.warpersan.create_insights.helpers;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import net.createmod.catnip.data.Iterate;

import javax.annotation.Nullable;

/**
 * Class responsible to offer functions for structures
 */
public class StructureHelper
{

    /**
     * Gets the controller of the given crushing wheel
     */
    @Nullable
    public static CrushingWheelControllerBlockEntity getCrushingWheelController(CrushingWheelBlockEntity crushingWheel) {
        var level = crushingWheel.getLevel();

        if (level == null)
            return null;

        var state = crushingWheel.getBlockState();
        var pos = crushingWheel.getBlockPos();

        for (var d : Iterate.directions) {
            if (d.getAxis() == state.getValue(RotatedPillarKineticBlock.AXIS))
                continue;

            var relativePos = pos.relative(d);

            if (!AllBlocks.CRUSHING_WHEEL_CONTROLLER.has(level.getBlockState(relativePos)))
                continue;

            var entity = level.getBlockEntity(relativePos);

            if (!(entity instanceof CrushingWheelControllerBlockEntity crushingWheelController))
                continue;

            return crushingWheelController;
        }

        return null;
    }
}
