package com.jamalam360.registry;

import com.jamalam360.ReapingHelper;
import com.jamalam360.compat.HarvestScythes;
import net.fabricmc.loader.api.FabricLoader;
import wraith.harvest_scythes.ScytheTool;

/**
 * @author Jamalam360
 */
public class CompatRegistry {
    public static void register(){
        if(FabricLoader.getInstance().isModLoaded("harvest_scythes")){
            HarvestScythes.registerCompat();
        }
    }
}
