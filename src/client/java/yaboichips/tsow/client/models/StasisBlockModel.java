package yaboichips.tsow.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import yaboichips.tsow.common.blocks.statis.StasisBlockEntity;

import static yaboichips.tsow.TSoW.MODID;

public class StasisBlockModel extends GeoModel<StasisBlockEntity> {
    @Override
    public ResourceLocation getModelResource(StasisBlockEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "geo/stasis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StasisBlockEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/stasis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StasisBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "animations/stasis.animation.json");
    }

    @Override
    public @Nullable RenderType getRenderType(StasisBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(getTextureResource(animatable));
    }
}
