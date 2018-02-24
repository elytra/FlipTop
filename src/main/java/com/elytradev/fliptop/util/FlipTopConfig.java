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

    @ConfigValue(type = Property.Type.BOOLEAN, category = "GauntletCrafting", comment = "Set true to disable the ability to make press plates in a crafting table.")
    public static boolean disablePlateCrafting = false;
    @ConfigValue(type = Property.Type.BOOLEAN, category = "GauntletCrafting", comment = "Set true to require a full 3x3 of iron gauntlets to make a compressed gauntlet.")
    public static boolean trueCompression = false;

    @ConfigValue(type = Property.Type.INTEGER, category = "GauntletUsage", comment = "The time needed to charge standard gauntlets. Compressed gauntlets will always take twice this time.")
    public static double chargeTime = 60;

    @ConfigValue(type = Property.Type.DOUBLE, category = "CompressedGauntlet", comment = "The velocity a compressed gauntlet give to its target. Anything above 5.0 might cause hostile mobs to despawn midair.")
    public static double compressedMoonshot = 5.0;
    @ConfigValue(type = Property.Type.DOUBLE, category = "CompressedGauntlet", comment = "How much a melee attack does with the compressed gauntlet.")
    public static double compressedAttack = 6.0;
    @ConfigValue(type = Property.Type.BOOLEAN, category = "CompressedGauntlet", comment = "Set false to make it impossible to repair compressed gauntlets in an anvil.")
    public static boolean canRepairCompressed = true;

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
