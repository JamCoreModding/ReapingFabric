package com.jamalam360.config;

import com.jamalam360.ReapingModInit;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

/**
 * @author Jamalam360
 */

@Config(name = ReapingModInit.MOD_ID)
public class ReapingModConfig implements ConfigData {
    public boolean enableDispenserBehavior = true;
}
