package yaboichips.tsow.client.util.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import yaboichips.tsow.core.TSoWSounds;

public class WindTickableSound extends AbstractTickableSoundInstance {
    public WindTickableSound() {
        super(TSoWSounds.WIND_LOOP, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.looping = true;
        this.delay = 0;
        this.volume = 0.6f;
        this.pitch = 1.0f;
    }

    @Override
    public void tick() {
        if (isStopped()) {
            this.stop();
        }
    }
}
