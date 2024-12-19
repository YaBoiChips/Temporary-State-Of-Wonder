package yaboichips.tsow.client.util.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class SoundHandler {
    private static final Map<ResourceKey<Level>, DimensionSoundInstance> activeSounds = new HashMap<>();
    private static final Map<ResourceKey<Level>, SoundEvent> dimensionSounds = new HashMap<>();

    /**
     * Registers a sound to be played in a specific dimension.
     *
     * @param dimension  The dimension's ResourceLocation (e.g., "minecraft:the_end").
     * @param soundEvent The SoundEvent to play in the dimension.
     */
    public static void registerDimensionSound(ResourceKey<Level> dimension, SoundEvent soundEvent) {
        dimensionSounds.put(dimension, soundEvent);
    }

    public static void tick(Minecraft client) {
        if (client.player == null || client.level == null) return;

        var soundManager = client.getSoundManager();
        ResourceKey<Level> currentDimension = client.level.dimension();

        // Handle all registered dimensions
        for (Map.Entry<ResourceKey<Level>, SoundEvent> entry : dimensionSounds.entrySet()) {
            ResourceKey<Level> dimension = entry.getKey();
            SoundEvent sound = entry.getValue();

            if (dimension.equals(currentDimension)) {
                // Start or continue playing the sound for this dimension
                if (!activeSounds.containsKey(dimension) || !soundManager.isActive(activeSounds.get(dimension))) {
                    DimensionSoundInstance soundInstance = new DimensionSoundInstance(sound, client.player.getRandom());
                    activeSounds.put(dimension, soundInstance);
                    soundManager.play(soundInstance);
                }
            } else {
                // Stop the sound if the player is no longer in this dimension
                if (activeSounds.containsKey(dimension)) {
                    activeSounds.get(dimension).stop();
                    activeSounds.remove(dimension);
                }
            }
        }
    }
}
