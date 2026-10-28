package com.cinematicui.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Unique private long cinematicUI$openedAt = System.currentTimeMillis();

    @Inject(method = "init()V", at = @At("HEAD"))
    private void cinematicUI$resetAnimation(CallbackInfo ci) {
        cinematicUI$openedAt = System.currentTimeMillis();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void cinematicUI$fadeIn(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        long elapsed = System.currentTimeMillis() - cinematicUI$openedAt;
        if (elapsed >= 220L) return;
        float progress = Math.max(0.0f, Math.min(1.0f, elapsed / 220.0f));
        int alpha = Math.round((1.0f - progress) * 115.0f);
        int color = (alpha << 24);
        Screen screen = (Screen) (Object) this;
        context.fill(0, 0, screen.width, screen.height, color);
    }
}
