package org.tlesis.squakeplusplus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tlesis.squakeplusplus.config.SquakeOptions;

public class SquakePlusPlus implements ModInitializer {
    public static final String MOD_ID = "squakeplusplus";
    public static final Logger logger = LogManager.getLogger(MOD_ID);
    public static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    public static final String MOD_NAME = "Squake++";

    @Override
    public void onInitialize() {
        SquakeOptions.tryLoad(logger::error);
        SquakeOptions.trySave(logger::error);
    }
}
