package yaboichips.tsow.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import yaboichips.tsow.client.renderers.ClockRenderer;
import yaboichips.tsow.client.renderers.StasisBlockRenderer;
import yaboichips.tsow.core.TSoWBlockEntities;
import yaboichips.tsow.core.TSoWBlocks;
import yaboichips.tsow.core.TSoWEntities;

import java.util.HashMap;

import static yaboichips.tsow.client.util.SkyRenderer.renderSimpleObject;
import static yaboichips.tsow.client.util.SkyRenderer.renderSky;

public class TSoWClient implements ClientModInitializer {
    public static final String MODID = "tsow";

    public static final ResourceLocation CUSTOM_SKY_TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/sky/sky.png");
    public static final ResourceLocation AB_SKY_TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/sky/abandonment_sky.png");

    public static final ResourceLocation CUSTOM_PLANET_TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/sky/planet.png");
    public static final ResourceLocation CUSTOM_PLANET_TEXTURE2 = ResourceLocation.fromNamespaceAndPath(MODID, "textures/sky/planet2.png");
    public static final ResourceLocation CUSTOM_PLANET_TEXTURE3 = ResourceLocation.fromNamespaceAndPath(MODID, "textures/sky/planet3.png");

    public static ResourceKey<Level> INTERSTELLAR = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, "interstellar"));
    public static ResourceKey<Level> ABANDONMENT = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, "abandonment"));

    public HashMap<Block, RenderType> BLOCKS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        renderSkys();
        registerRenderers();
        registerBlockCutouts();
    }

    public void registerBlockCutouts(){
        BLOCKS.put(TSoWBlocks.SNOWDROP, RenderType.cutout());
        BLOCKS.forEach(BlockRenderLayerMap.INSTANCE::putBlock);
    }

    public void registerRenderers() {
        EntityRendererRegistry.register(TSoWEntities.CLOCK, ClockRenderer::new);
        BlockEntityRenderers.register(TSoWBlockEntities.STASIS_BLOCK_ENTITY, StasisBlockRenderer::new);

    }

    public void renderSkys(){
        DimensionRenderingRegistry.registerSkyRenderer(INTERSTELLAR, (worldRenderContext) -> {
            PoseStack poseStack = new PoseStack();
            poseStack.mulPose(worldRenderContext.positionMatrix());
            renderSky(CUSTOM_SKY_TEXTURE, poseStack, 2.0f);
        });
        DimensionRenderingRegistry.registerSkyRenderer(ABANDONMENT, (worldRenderContext) -> {
            PoseStack poseStack = new PoseStack();
            poseStack.mulPose(worldRenderContext.positionMatrix());
            renderSky(AB_SKY_TEXTURE, poseStack, 1.0f);
            renderSimpleObject(CUSTOM_PLANET_TEXTURE, 0,-90, 100, poseStack);
            renderSimpleObject(CUSTOM_PLANET_TEXTURE2, -50,130, 600, poseStack);
            renderSimpleObject(CUSTOM_PLANET_TEXTURE3, 35,188, 500, poseStack);
        });
    }
}
