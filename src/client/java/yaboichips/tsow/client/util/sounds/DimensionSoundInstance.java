package yaboichips.tsow.client.util.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class DimensionSoundInstance extends AbstractTickableSoundInstance {
    public DimensionSoundInstance(SoundEvent event, RandomSource randomSource) {
        super(event, SoundSource.AMBIENT, randomSource);
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0f;
        this.pitch = 1.0f;
    }

    @Override
    public void tick() {
        if (isStopped()) {
            this.stop();
        }
    }
}
