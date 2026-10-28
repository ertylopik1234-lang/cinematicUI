package com.cinematicui;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CinematicUI implements ClientModInitializer {
    public static final String MOD_ID = "cinematicui";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("cinematicUI loaded — cinematic camera and screen animations enabled.");
    }
}
