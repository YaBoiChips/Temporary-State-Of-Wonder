package yaboichips.tsow.client.models;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import yaboichips.tsow.common.entities.GiantEntity;

import static yaboichips.tsow.TSoW.MODID;

public class GiantModel<T extends GiantEntity> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        if (object.isChild()) {
            return ResourceLocation.fromNamespaceAndPath(MODID, "geo/baby_giant.geo.json");
        } else {
            return ResourceLocation.fromNamespaceAndPath(MODID, "geo/giant.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/giant.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if (animatable.isChild()) {
            return ResourceLocation.fromNamespaceAndPath(MODID, "animations/baby_giant.animation.json");
        } else {
            return ResourceLocation.fromNamespaceAndPath(MODID, "animations/giant.animation.json");
        }
    }


//    @Override
//    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
//        super.setCustomAnimations(animatable, instanceId, animationState);
//        GeoBone head = this.getAnimationProcessor().getBone("head");
//        head.setRotX(animatable.getXRot() * ((float) Math.PI / 180F));
//        head.setRotY(animatable.yHeadRot * ((float) Math.PI / 180F));
//    }
}
