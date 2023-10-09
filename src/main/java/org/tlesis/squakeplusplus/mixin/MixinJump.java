package org.tlesis.squakeplusplus.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tlesis.squakeplusplus.config.SquakeOptions;

@Mixin(LivingEntity.class)
public abstract class MixinJump {
    @Shadow
    private int jumpingCooldown;

    @Inject(method = "tickMovement", at = @At(value = "HEAD"))
    public void tickMovement(CallbackInfo ci) {
        if (SquakeOptions.INSTANCE.jumpSpam) {
            jumpingCooldown = 0;
        }
    }
}