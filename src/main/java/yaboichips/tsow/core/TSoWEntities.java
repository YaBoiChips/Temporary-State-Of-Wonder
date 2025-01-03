package yaboichips.tsow.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import yaboichips.tsow.common.entities.ClockEntity;
import yaboichips.tsow.common.entities.GiantEntity;

import static yaboichips.tsow.TSoW.MODID;

public class TSoWEntities {

    public static final EntityType<ClockEntity> CLOCK = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(MODID, "clock"),
            EntityType.Builder.of(ClockEntity::new, MobCategory.MONSTER).sized(1.1f, 1.1f).build("clock"));

    public static final EntityType<GiantEntity> GIANT = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(MODID, "giant"),
            EntityType.Builder.of(GiantEntity::new, MobCategory.MONSTER).sized(1.5f, 6f).build("giant"));
}
