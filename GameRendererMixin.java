package com.cinematicui.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "bobView", at = @At("TAIL"))
    private void cinematicUI$addCameraSway(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player == null || client.options == null || !player.isOnGround() || player.isSneaking()) return;

        double speed = player.getVelocity().horizontalLength();
        if (speed < 0.035) return;
        float intensity = (float) Math.min(1.0, speed * 3.2);
        float phase = (player.age + tickDelta) * 0.72f;
        float roll = (float) Math.sin(phase) * 0.42f * intensity;
        float pitch = (float) Math.cos(phase * 0.5f) * 0.22f * intensity;
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(roll));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitch));
    }
}
