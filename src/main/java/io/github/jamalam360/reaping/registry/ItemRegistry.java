/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Jamalam360
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.jamalam360.reaping.registry;

import io.github.jamalam360.reaping.ReaperItem;
import io.github.jamalam360.reaping.ReapingHelper;
import io.github.jamalam360.reaping.ReapingToolDispenserBehavior;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;

import static io.github.jamalam360.reaping.ReapingModInit.idOf;
import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraft.util.registry.Registry.register;

/**
 * @author Jamalam360
 */
public class ItemRegistry {
    public static final Item IRON_REAPING_TOOL_ITEM = new ReaperItem(reaperBaseProperties(), ToolMaterials.IRON);
    public static final Item GOLD_REAPING_TOOL_ITEM = new ReaperItem(reaperBaseProperties(), ToolMaterials.GOLD);
    public static final Item DIAMOND_REAPING_TOOL_ITEM = new ReaperItem(reaperBaseProperties(), ToolMaterials.DIAMOND);
    public static final Item NETHERITE_REAPING_TOOL_ITEM = new ReaperItem(reaperBaseProperties().fireproof(), ToolMaterials.NETHERITE);

    public static void registerItems() {
        registerReapingTool(IRON_REAPING_TOOL_ITEM, idOf("iron_reaping_tool"));
        registerReapingTool(GOLD_REAPING_TOOL_ITEM, idOf("gold_reaping_tool"));
        registerReapingTool(DIAMOND_REAPING_TOOL_ITEM, idOf("diamond_reaping_tool"));
        registerReapingTool(NETHERITE_REAPING_TOOL_ITEM, idOf("netherite_reaping_tool"));
    }

    private static FabricItemSettings reaperBaseProperties() {
        return new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1);
    }

    private static void registerReapingTool(Item item, Identifier id) {
        register(ITEM, id, item);
        DispenserBlock.registerBehavior(item, new ReapingToolDispenserBehavior());
        ReapingHelper.registerValidReapingTool(item.getClass());
    }
}
