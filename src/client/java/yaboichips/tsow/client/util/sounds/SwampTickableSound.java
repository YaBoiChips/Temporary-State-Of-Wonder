package yaboichips.tsow.client.util.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import yaboichips.tsow.core.TSoWSounds;

public class SwampTickableSound extends AbstractTickableSoundInstance {
    public SwampTickableSound(RandomSource randomSource) {
        super(TSoWSounds.SWAMP_AMBIENT, SoundSource.AMBIENT, randomSource);
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
