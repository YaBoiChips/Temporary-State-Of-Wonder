package yaboichips.tsow.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.resources.ResourceLocation;
import yaboichips.tsow.client.util.SkyRenderUtils;

public class TSoWClient implements ClientModInitializer {
    public static final String MODID = "tsow";

    private static final ResourceLocation CUSTOM_SKY_TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/sky/abyss_sky.png");

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.LAST.register(context -> {
            // Ensure we have valid context and the player is in the target dimension
            SkyRenderUtils.renderSkybox(context.matrixStack(), CUSTOM_SKY_TEXTURE);
        });
    }
}

