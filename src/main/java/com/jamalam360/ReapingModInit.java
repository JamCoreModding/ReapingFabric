package com.jamalam360;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReapingModInit implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "reapingmod";
    public static final String MOD_NAME = "Reaping Mod";

    public static final Item IRON_REAPING_TOOL_ITEM = new ReaperItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), ToolMaterials.IRON);
    public static final Item GOLD_REAPING_TOOL_ITEM = new ReaperItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), ToolMaterials.GOLD);
    public static final Item DIAMOND_REAPING_TOOL_ITEM = new ReaperItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), ToolMaterials.DIAMOND);
    public static final Item NETHERITE_REAPING_TOOL_ITEM = new ReaperItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), ToolMaterials.NETHERITE);

    @Override
    public void onInitialize() {
        registerReapingToolItem(IRON_REAPING_TOOL_ITEM, idOf("iron_reaping_tool"));
        registerReapingToolItem(GOLD_REAPING_TOOL_ITEM, idOf("gold_reaping_tool"));
        registerReapingToolItem(DIAMOND_REAPING_TOOL_ITEM, idOf("diamond_reaping_tool"));
        registerReapingToolItem(NETHERITE_REAPING_TOOL_ITEM, idOf("netherite_reaping_tool"));

        log(Level.INFO, "Initializing");
    }

    private Identifier idOf(String name) {
        return new Identifier(MOD_ID, name);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    private static void registerReapingToolItem(Item item, Identifier id){
        Registry.register(Registry.ITEM, id, item);
        DispenserBlock.registerBehavior(item, new ReapingToolDispenserBehavior());
    }
}