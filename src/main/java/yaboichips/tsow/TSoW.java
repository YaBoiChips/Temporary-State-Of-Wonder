package yaboichips.tsow;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import yaboichips.tsow.common.entities.ClockEntity;
import yaboichips.tsow.common.entities.GiantEntity;
import yaboichips.tsow.core.TSoWBlocks;
import yaboichips.tsow.core.TSoWEntities;
import yaboichips.tsow.core.TSoWItems;
import yaboichips.tsow.core.TSoWSounds;

public class TSoW implements ModInitializer {

    public static final String MODID = "tsow";

    public static final float Y_MOTION_MULTIPLIER = 0.95F;
    public static final float JUMP_HEIGHT_MODIFIER = 11F;


    public static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MODID, "tab"));

    public static final CreativeModeTab TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.ACACIA_BOAT))
            .title(Component.translatable("itemGroup.tsow"))
            .build();

    public static ResourceKey<Level> INTERSTELLAR = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, "interstellar"));
    public static ResourceKey<Level> GIANTS_SWAMP = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, "giants_swamp"));
    public static ResourceKey<Level> ABANDONMENT = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, "abandonment"));


    @Override
    public void onInitialize() {
        registerAttributes();
        registerSpawns();
        registerSounds();
        registerBlocks();
        registerItems();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, TAB);
    }

    public void registerAttributes() {
        FabricDefaultAttributeRegistry.register(TSoWEntities.CLOCK, ClockEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TSoWEntities.GIANT, GiantEntity.createAttributes());
    }

    public void registerSounds() {
        TSoWSounds.SOUNDS.forEach((soundEvent, resourceLocation) -> Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, soundEvent));
    }

    public void registerBlocks() {
        TSoWBlocks.BLOCKS.forEach((block, resourceLocation) -> Registry.register(BuiltInRegistries.BLOCK, resourceLocation, block));
    }

    public void registerItems() {
        TSoWBlocks.BLOCKS.forEach((block, resourceLocation) -> {
            Item item = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, resourceLocation, item);
            ItemGroupEvents.modifyEntriesEvent(TAB_KEY).register(itemGroup -> {
                itemGroup.prepend(item);
            });
        });
        TSoWItems.ITEMS.forEach((item, resourceLocation) -> {
            Registry.register(BuiltInRegistries.ITEM, resourceLocation, item);
            ItemGroupEvents.modifyEntriesEvent(TAB_KEY).register(itemGroup -> {
                itemGroup.prepend(item);
            });
        });
    }

    public void registerSpawns() {
        SpawnPlacements.register(TSoWEntities.CLOCK, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ClockEntity::canSpawn);
    }

    public static Vec3 modifyGravityFallSpeed(Vec3 motion, Entity entity) {
        if (entity instanceof Player player) {
            if (player.isCreative())
                return motion;
        }

        double modifiedYMotion = motion.y * Y_MOTION_MULTIPLIER;
        return new Vec3(motion.x, modifiedYMotion, motion.z);
    }

    public static void modifyJumpFactor(LivingEntity entity, float upwardsMotion) {
        float jumpHeight = upwardsMotion * JUMP_HEIGHT_MODIFIER;

        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x, jumpHeight, motion.z);
        if (entity.isSprinting()) {
            float f1 = entity.getYRot() * ((float) Math.PI / 180F);
            entity.setDeltaMovement(entity.getDeltaMovement().add(-Mth.sin(f1) * 0.2F, 0.0D, Mth.cos(f1) * 0.2F));
        }
    }
}
