package yaboichips.tsow.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;

import static yaboichips.tsow.TSoW.MODID;

public class TSoWSounds {

    public static HashMap<SoundEvent, ResourceLocation> SOUNDS = new HashMap<>();
    public static SoundEvent CLOCK_AMBIENT = register(ResourceLocation.fromNamespaceAndPath(MODID, "clock_ambient"));
    public static SoundEvent CLOCK_DIE = register(ResourceLocation.fromNamespaceAndPath(MODID, "clock_die"));

    public static SoundEvent register(ResourceLocation id) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(id);
        SOUNDS.put(event, id);
        return event;
    }

}
