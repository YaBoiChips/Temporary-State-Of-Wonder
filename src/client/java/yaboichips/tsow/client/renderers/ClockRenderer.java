package yaboichips.tsow.client.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import yaboichips.tsow.common.entities.ClockEntity;
import yaboichips.tsow.client.models.ClockModel;

public class ClockRenderer extends GeoEntityRenderer<ClockEntity> {
    public ClockRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ClockModel<>());
    }
}

