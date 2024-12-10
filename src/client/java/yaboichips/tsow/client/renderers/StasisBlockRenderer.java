package yaboichips.tsow.client.renderers;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import yaboichips.tsow.common.blocks.statis.StasisBlockEntity;
import yaboichips.tsow.client.models.StasisBlockModel;

public class StasisBlockRenderer extends GeoBlockRenderer<StasisBlockEntity> {
    public StasisBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new StasisBlockModel());
    }
}
