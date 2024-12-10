package yaboichips.tsow.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import yaboichips.tsow.common.blocks.statis.StasisBlock;

import java.util.HashMap;

import static yaboichips.tsow.TSoW.MODID;

public class TSoWBlocks {

    public static HashMap<Block, ResourceLocation> BLOCKS = new HashMap<>();

    public static final Block ALUMINIUM = registerMetal("aluminium");
    public static final Block CRACKED_TILE = registerMetal("cracked_tile");
    public static final Block DARK_PANEL = registerMetal("dark_panel");
    public static final Block DARK_TILE = registerMetal("dark_tile");
    public static final Block LIGHT_PANEL = registerMetal("light_panel");
    public static final Block LIGHT_TILE = registerMetal("light_tile");
    public static final Block THALLIUM = registerMetal("thallium");
    public static final Block TILE = registerMetal("tile");
    public static final Block TIN = registerMetal("tin");
    public static final Block STASIS_BLOCK = register(new StasisBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE)), "stasis");
    public static final Block SNOWDROP = register(new FlowerBlock(MobEffects.NIGHT_VISION, 5.0F, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).lightLevel(light -> 8)), "snowdrop");

    public static Block registerMetal(String id) {
        return register(new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)), id);
    }

    public static Block register(Block block, String id) {
        BLOCKS.put(block, ResourceLocation.fromNamespaceAndPath(MODID, id));
        return block;
    }
}
