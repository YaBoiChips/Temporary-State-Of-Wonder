package yaboichips.tsow.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yaboichips.tsow.client.util.SkyRenderUtils;

import static yaboichips.tsow.client.TSoWClient.MODID;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Unique
    private static final ResourceLocation CUSTOM_SKY_TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/sky/abyss_sky.png");


    @Inject(method = "renderSky", at = @At(value = "HEAD"), cancellable = true)
    private void renderSky(Matrix4f projectionMatrix, Matrix4f viewMatrix, float f, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci){
        ci.cancel();
        runnable.run();
        // Bind the custom texture
        RenderSystem.setShaderTexture(0, CUSTOM_SKY_TEXTURE);

        // Render all 4 sides
        // Render the skybox for all 4 sides
        renderSide(projectionMatrix, 0);    // Front
        renderSide(projectionMatrix, 90);   // Right
        renderSide(projectionMatrix, 180);  // Back
        renderSide(projectionMatrix, 270);  // Left
    }

    private void renderSide(Matrix4f projectionMatrix, int rotation) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        RenderSystem.setTextureMatrix(projectionMatrix);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Apply rotation for the current side
        Matrix4f rotationMatrix = new Matrix4f();
        rotationMatrix.identity();
        rotationMatrix.translate(0.0f, 0.0f, -1.0f);
        rotationMatrix.rotate(Axis.YP.rotationDegrees(rotation));

        // Add vertices for a quad
        buffer.addVertex(rotationMatrix, -1.0f, -1.0f, -1.0f).setUv(0.0f, 0.0f);
        buffer.addVertex(rotationMatrix, 1.0f, -1.0f, -1.0f).setUv(1.0f, 0.0f);
        buffer.addVertex(rotationMatrix, 1.0f, 1.0f, -1.0f).setUv(1.0f, 1.0f);
        buffer.addVertex(rotationMatrix, -1.0f, 1.0f, -1.0f).setUv(0.0f, 1.0f);

        tesselator.clear();
    }
}
