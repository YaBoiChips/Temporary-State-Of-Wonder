package yaboichips.tsow.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import yaboichips.tsow.common.blocks.statis.StasisBlockEntity;

import static yaboichips.tsow.TSoW.MODID;

public class TSoWBlockEntities {

    public static final BlockEntityType<StasisBlockEntity> STASIS_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "stasis_block_entity"),
            BlockEntityType.Builder.of(StasisBlockEntity::new, TSoWBlocks.STASIS_BLOCK).build(null));
}
