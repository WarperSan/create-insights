package dev.warpersan.create_insights.tooltips.builders;

import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;

/**
 * Builder used to convert a number into a readable format
 */
public class UnitBuilder
{
	/**
	 * Enum used to define the unit of the number
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public enum UnitType
	{
		MILLIBUCKETS,
		BUCKETS,
		DEGREES,
		RPM,
		STRESS,
		TICKS,
		SECONDS,
		MINUTES,
		HOURS,
	}

	private @Nullable UnitType unit;
	private double value;

	public UnitBuilder()
	{
		this.unit = null;
		this.value = 0;
	}

	/**
	 * Sets the unit type to the given type
	 */
	public UnitBuilder ofUnit(UnitType unit)
	{
		this.unit = unit;
		return this;
	}

	/**
	 * Sets the value to the given value
	 */
	public UnitBuilder forValue(double value)
	{
		this.value = value;
		return this;
	}

	/**
	 * Gets the display unit of the given type for the given value 
	 */
	private static MutableComponent getUnitComponent(UnitType type, double value)
	{
		LangBuilder unitBuilder;

		switch (type)
		{
			case MILLIBUCKETS -> unitBuilder = CreateLang.translate("generic.unit.millibuckets");
			case BUCKETS -> unitBuilder = CreateLang.translate("generic.unit.buckets");
			case DEGREES -> unitBuilder = CreateLang.translate("generic.unit.degrees");
			case RPM -> unitBuilder = CreateLang.translate("generic.unit.rpm");
			case STRESS -> unitBuilder = CreateLang.translate("generic.unit.stress");
			case TICKS -> unitBuilder = new InsightsBuilder().translate("tooltip.generic.unit.ticks");
			case SECONDS -> unitBuilder = new InsightsBuilder().translate("tooltip.generic.unit.seconds");
			case MINUTES -> unitBuilder = new InsightsBuilder().translate("tooltip.generic.unit.minutes");
			case HOURS -> unitBuilder = new InsightsBuilder().translate("tooltip.generic.unit.hours");
			default -> throw new IllegalStateException("Unexpected value: " + type);
		}

		var builder = new InsightsBuilder();

		builder.text(LangNumberFormat.format(value));
		builder.add(unitBuilder);

		return builder.component();
	}

	/**
	 * Creates a component with the gathered information
	 */
	public MutableComponent component()
	{
		if (this.unit == null)
			throw new IllegalStateException("No unit was specified to builder");

		return getUnitComponent(
				this.unit,
				this.value
		);
	}
}
