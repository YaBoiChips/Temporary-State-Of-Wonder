package yaboichips.tsow.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkyRenderUtils {
    /**
     * DO NOT PUSH AND POP MATRIX STACKS BEFORE AND AFTER THE USAGE OF THIS METHOD
     */
    public static void renderSkybox(PoseStack poseStack, ResourceLocation texture) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.resetTextureMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);

        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, VertexFormat.builder().add("tex", VertexFormatElement.POSITION).build());

        // Render the skybox cube
        for (int face = 0; face < 6; face++) {
            poseStack.pushPose();
            applyFaceRotation(poseStack, face);
            drawQuad(bufferBuilder);
            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    private static void applyFaceRotation(PoseStack poseStack, int face) {
        switch (face) {
            case 0 -> poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-90.0F)); // Bottom
            case 1 -> poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90.0F));  // Top
            case 2 -> poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F)); // Front
            case 3 -> poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(90.0F));  // Left
            case 4 -> poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-90.0F));// Right
            case 5 -> poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(0.0F));   // Back
        }
    }

    private static void drawQuad(BufferBuilder bufferBuilder) {

        bufferBuilder.addVertex(-1.0f, -1.0f, -1.0f).setUv(0.0F, 0.0F);
        bufferBuilder.addVertex(-1.0f, 1.0f, -1.0f).setUv(0.0F, 1.0F);
        bufferBuilder.addVertex(1.0f, 1.0f, -1.0f).setUv(1.0F, 1.0F);
        bufferBuilder.addVertex(1.0f, -1.0f, -1.0f).setUv(1.0F, 0.0F);

        Tesselator.getInstance().clear();
    }
}
