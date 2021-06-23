package com.jamalam360.registry;

import com.jamalam360.ReaperItem;
import com.jamalam360.ReapingHelper;
import com.jamalam360.ReapingToolDispenserBehavior;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;

import static com.jamalam360.ReapingModInit.idOf;
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
