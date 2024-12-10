package yaboichips.tsow.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

import static yaboichips.tsow.client.TSoWClient.CUSTOM_SKY_TEXTURE;

public class SkyRenderer {

    public static void renderSky(PoseStack poseStack) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, CUSTOM_SKY_TEXTURE);
        Tesselator tesselator = Tesselator.getInstance();

        for (int i = 0; i < 6; ++i) {
            poseStack.pushPose();
            if (i == 1) {
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90.0F).rotateY(1.57f));
            }

            if (i == 2) {
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-90.0F).rotateY(-1.57f));
            }

            if (i == 3) {
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(180.0F).rotateY(1.57f));
            }

            if (i == 4) {
                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(-90.0F).rotateY(3.14f));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(255, 255, 255, 255);
            bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, 2.0F).setColor(255, 255, 255, 255);
            bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(2.0F, 2.0F).setColor(255, 255, 255, 255);
            bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(2.0F, 0.0F).setColor(255, 255, 255, 255);
            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }
}
