package yaboichips.tsow.client.models;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import yaboichips.tsow.common.entities.ClockEntity;

import static yaboichips.tsow.TSoW.MODID;

public class ClockModel<T extends ClockEntity> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "geo/clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/clock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return null;
    }
}
