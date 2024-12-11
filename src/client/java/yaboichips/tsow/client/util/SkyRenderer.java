package yaboichips.tsow.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import static yaboichips.tsow.client.TSoWClient.CUSTOM_SKY_TEXTURE;

public class SkyRenderer {

    public static void renderSky(ResourceLocation texture, PoseStack poseStack, float UV) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, texture);
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
            bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, UV).setColor(255, 255, 255, 255);
            bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(UV, UV).setColor(255, 255, 255, 255);
            bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(UV, 0.0F).setColor(255, 255, 255, 255);
            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    public static void renderSimpleObject(ResourceLocation objectTexturePath, float zAngle,float xAngle, float size, PoseStack poseStack) {
        Tesselator tessellator = Tesselator.getInstance();
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, objectTexturePath);
        poseStack.pushPose();

        poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(zAngle));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(xAngle));

        Matrix4f matrix4f = poseStack.last().pose();

        BufferBuilder bufferbuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        bufferbuilder.addVertex(matrix4f, -100.0F/*Up and Down texture size*/, -size/*Size of the texture or Distance from the player*/, -100.0F/*Left to right texture size*/).setUv(0.0F/*Horizontal Axis*/, 0.0F/*Vertical Axis*/).setColor(255, 45, 45, 155);
        bufferbuilder.addVertex(matrix4f, -100.0F, -size, 100.0F).setUv(0.0F, 1.0F).setColor(255, 255, 255, 255);
        bufferbuilder.addVertex(matrix4f, 100.0F, -size, 100.0F).setUv(1.0F, 1.0F).setColor(255, 255, 255, 255);
        bufferbuilder.addVertex(matrix4f, 100.0F, -size, -100.0F).setUv(1.0F, 0.0F).setColor(255, 255, 255, 255);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        poseStack.popPose();

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();

    }
}
