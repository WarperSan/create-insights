# Tooltip Design Decisions

This document will go over what decisions were made to decide what can be a tooltip and how it has to be made.

## Tooltip worth vs not worth

When deciding if a tooltip is worth making, it is important to check if the player can have **access to the information in another way**.

For example, it would be useful to have the RPM displayed on cogs. However, the player **already has access** to this information. A speedometer can be used for that purpose.

Even though the information is not as easily readable (as it requires extra steps), the **information is readable**.

A counter example is the remaining burn time on blaze burners. The player knows whether a burner is active or not. However, there is **no way to know** how much time is left. There is no block in the game that allows to read that information. It *is* possible to deduce it from deployers and the wiki, but this is farfetched.

## What to display?

Not every information is worth or should be displayed to the player.

When choosing what information to display, it is recommended to skip most of internal information. For example, the player doesn't need to know how exactly the pulse blocks are made. Information should always be relevant to the player.

