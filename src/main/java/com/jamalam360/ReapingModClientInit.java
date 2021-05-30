package com.jamalam360;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class ReapingModClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0xe1de05, ReapingModInit.GOLD_REAPING_TOOL_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x8ef3e2, ReapingModInit.DIAMOND_REAPING_TOOL_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x5a575a, ReapingModInit.NETHERITE_REAPING_TOOL_ITEM);
    }
}
