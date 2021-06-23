package com.jamalam360.compat;

import com.jamalam360.ReapingHelper;
import wraith.harvest_scythes.ScytheTool;

/**
 * @author Jamalam360
 */
public class HarvestScythes {
    public static void registerCompat(){
        ReapingHelper.registerValidReapingTool(ScytheTool.class);
    }
}
