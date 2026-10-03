# How to add a Tooltip Provider?

This mod uses a system of providers to generate the tooltips. This allows to have modular tooltips, as each tooltip is
independent of the other.

## Choosing the type

Before adding a new provider, it is important to know what type of tooltip we want to add.

If you want the best control over the tooltip, implement `ITooltipProvider`. This is the lowest point in the tooltip
chain, as the tooltip system interacts with it directly.

If you want to simply add a tooltip to the `Insights` category, you can extend `InsightsTooltipProvider`. The class
automatically handles the indentation and puts all tooltips under the same category.

If you want a collection of tooltips under the same category, you can copy what the built-in category does.

## Implementing the provider

When implementing the provider, you will be required to define `provide()`. This will be called whenever a tooltip will
be displayed.

A `TooltipContext` instance will be passed with the call. It gives information about the tooltip context. For example,
you can
access the player who triggered it, the block entity being targeted, etc. This is useful when needing to filter the
calls, as you might not want your tooltip to be displayed permanently.

Once you have created your components, you can call `TooltipContext.add()` or `TooltipContext.addAll()` to append your
tooltip to the list of components.

> [!IMPORTANT]
> It isn't possible to insert components into the tooltip. You can only add to the end.

## Registering the provider

To register a provider, you simply need to call `GoggleTooltipCollector.addProvider()` while passing an instance of your
provider.