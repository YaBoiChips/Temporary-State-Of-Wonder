package yaboichips.tsow.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;

import static yaboichips.tsow.TSoW.MODID;

public class TSoWSounds {

    public static HashMap<SoundEvent, ResourceLocation> SOUNDS = new HashMap<>();
    public static final SoundEvent CLOCK_AMBIENT = register(ResourceLocation.fromNamespaceAndPath(MODID, "clock_ambient"));
    public static final SoundEvent CLOCK_DIE = register(ResourceLocation.fromNamespaceAndPath(MODID, "clock_die"));
    public static final SoundEvent WIND_LOOP = register(ResourceLocation.fromNamespaceAndPath(MODID, "world/wind_loop"));
    public static final SoundEvent SWAMP_AMBIENT = register(ResourceLocation.fromNamespaceAndPath(MODID, "world/swamp_loop"));

    public static SoundEvent register(ResourceLocation id) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(id);
        SOUNDS.put(event, id);
        return event;
    }

}
