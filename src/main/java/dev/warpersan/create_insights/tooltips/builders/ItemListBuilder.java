package dev.warpersan.create_insights.tooltips.builders;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder used to convert an item handler into a readable format
 */
public class ItemListBuilder
{
	private final List<ItemStack> stacks;

	public ItemListBuilder()
	{
		this.stacks = new ArrayList<>();
	}

	/**
	 * Checks if the builder has gathered any stack
	 */
	public boolean hasStack()
	{
		return !this.stacks.isEmpty();
	}

	/**
	 * Adds the stacks from the given item handler
	 */
	public ItemListBuilder addItemHandler(@NotNull IItemHandler itemHandler)
	{
		for (var i = 0; i < itemHandler.getSlots(); i++)
		{
			var stack = itemHandler.getStackInSlot(i);

			if (stack.isEmpty())
				continue;

			addStack(stack);
		}

		return this;
	}

	/**
	 * Adds the given stack to the items to display
	 */
	public ItemListBuilder addStack(@NotNull ItemStack stack)
	{
		this.stacks.add(stack);
		return this;
	}

	/**
	 * Gets the display format of the given stack
	 */
	private static MutableComponent getStack(@NotNull ItemStack stack)
	{
		var name = Component.translatable(stack.getDescriptionId())
				.withStyle(ChatFormatting.DARK_GRAY);

		var quantity = Component.literal(" x" + stack.getCount())
				.withStyle(ChatFormatting.DARK_GREEN);

		var itemBuilder = new InsightsBuilder()
				.add(name)
				.add(quantity);

		return itemBuilder.component();
	}

	/**
	 * Gets the display format of the given stacks
	 */
	private static Iterable<MutableComponent> getStacks(Iterable<ItemStack> stacks)
	{
		var components = new ArrayList<MutableComponent>();

		for (var stack : stacks)
		{
			if (stack == null)
				continue;

			components.add(getStack(stack));
		}

		return components;
	}

	/**
	 * Creates components with the gathered information
	 */
	public Iterable<MutableComponent> components()
	{
		return getStacks(this.stacks);
	}
}
