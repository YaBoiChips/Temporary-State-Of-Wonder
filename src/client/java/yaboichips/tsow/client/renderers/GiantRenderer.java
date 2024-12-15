package yaboichips.tsow.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import yaboichips.tsow.client.models.ClockModel;
import yaboichips.tsow.client.models.GiantModel;
import yaboichips.tsow.common.entities.ClockEntity;
import yaboichips.tsow.common.entities.GiantEntity;

public class GiantRenderer extends GeoEntityRenderer<GiantEntity> {
    public GiantRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GiantModel<>());
    }

    @Override
    public void render(GiantEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(6, 6 ,6);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}

