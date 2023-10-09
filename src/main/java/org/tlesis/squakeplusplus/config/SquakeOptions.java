package org.tlesis.squakeplusplus.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class SquakeOptions {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FOLDER = FabricLoader.getInstance().getConfigDir();
    private static final Path CONFIG_FILE = CONFIG_FOLDER.resolve("squakeplusplussr.json");

    public static SquakeOptions INSTANCE = new SquakeOptions();

    public boolean enabled = true;
    public boolean uncappedBhop = true;
    public boolean shark = false;
    public boolean trimp = false;
    public boolean hl2OldEngineBhop = false;
    public boolean jumpSpam = true;
    public boolean fallDamageStop = false;
    public double softCapThreshold = 544.0;
    public double hardCapThreshold = 544.0;
    public double groundAccelerate = 10.0;
    public double airAccelerate = 10.0;
    public double maxAirAccelerationPerTick = 0.05;
    public double softCapDegen = 0.65;
    public double sharkSurfaceTension = 0.2;
    public double sharkWaterFriction = 0.1;
    public double trimpMultiplier = 1.4;

    private static void load() throws IOException {
        if (!Files.exists(CONFIG_FILE)) {
            return;
        }
        INSTANCE = GSON.fromJson(
                new String(Files.readAllBytes(CONFIG_FILE)),
                SquakeOptions.class
        );
    }

    public static boolean tryLoad(Consumer<IOException> exceptionConsumer) {
        try {
            load();
            return true;
        } catch (IOException e) {
            exceptionConsumer.accept(e);
            return false;
        }
    }

    private static void save() throws IOException {
        ensureConfigFolder();
        Files.write(CONFIG_FILE, GSON.toJson(INSTANCE).getBytes(StandardCharsets.UTF_8));
    }

    private static void ensureConfigFolder() throws IOException {
        if (!Files.isDirectory(CONFIG_FOLDER)) {
            // If "config" is a file then we need to delete it to make it a folder
            if (Files.isRegularFile(CONFIG_FOLDER)) {
                Files.delete(CONFIG_FOLDER);
            }
            Files.createDirectory(CONFIG_FOLDER);
        }
    }

    public static boolean trySave(Consumer<IOException> exceptionConsumer) {
        try {
            save();
            return true;
        } catch (IOException e) {
            exceptionConsumer.accept(e);
            return false;
        }
    }
}
