package org.tlesis.squakeplusplus.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tlesis.squakeplusplus.client.QuakeClientPlayer;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
    public void updateVelocityInject(float speed, Vec3d movementInput, CallbackInfo ci) {
        Entity player = ((Entity) (Object) this);
        if (QuakeClientPlayer.moveRelativeBase(player, (float) movementInput.x, (float) movementInput.z, speed)) {
            ci.cancel();
        }
    }
}
