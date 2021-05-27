package com.jamalam360;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReapingModInit implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "reapingmod";
    public static final String MOD_NAME = "Reaping Mod";

    private static final Item REAPING_TOOL_ITEM = new ReaperItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reaping_tool"), REAPING_TOOL_ITEM);

        DispenserBlock.registerBehavior(REAPING_TOOL_ITEM, new ReapingToolDispenserBehavior());

        log(Level.INFO, "Initializing");
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}