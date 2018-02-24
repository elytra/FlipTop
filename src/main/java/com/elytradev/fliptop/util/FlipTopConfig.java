package com.elytradev.fliptop.util;

import com.elytradev.fliptop.FlipTop;
import net.minecraftforge.common.config.Configuration;
import com.elytradev.concrete.config.ConcreteConfig;
import com.elytradev.concrete.config.ConfigValue;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class FlipTopConfig extends ConcreteConfig {

    public File configFolder;

//    @ConfigValue(type = Property.Type.INTEGER, category = "BoilerUsage", comment = "The amount of ticks needed for one boiler cycle, sans calculation. Actual value will be 200/(<number of firebox blocks> * <number of active fuel sources>.")
//    public static int ticksToBoil = 200;

    private FlipTopConfig(File configFile) {
        super(configFile, FlipTop.modId);
        this.configFolder = configFile.getParentFile();
    }

    public static FlipTopConfig createConfig(FMLPreInitializationEvent event) {
        //Move config file if it exists.
        File flipTopFolder = new File(event.getModConfigurationDirectory(), "fliptop");
        flipTopFolder.mkdirs();
        if (event.getSuggestedConfigurationFile().exists()) {
            event.getSuggestedConfigurationFile().renameTo(new File(flipTopFolder, "fliptop.cfg"));
        }

        FlipTopConfig config = new FlipTopConfig(new File(flipTopFolder, "fliptop.cfg"));
        config.loadConfig();
        return config;
    }
}
