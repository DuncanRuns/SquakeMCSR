package org.tlesis.squakeplusplus.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tlesis.squakeplusplus.config.SquakeOptions;

import static org.tlesis.squakeplusplus.client.QuakeClientPlayer.*;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "jump", at = @At("TAIL"))
    public void jumpInject(CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        afterJump(player);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travelInject(Vec3d movementInput, CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if (moveEntityWithHeading(player, (float) movementInput.x, (float) movementInput.z)) {
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickInject(CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        beforeOnLivingUpdate(player);
    }

    @Inject(method = "handleFallDamage", at = @At("RETURN"))
    private void a(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> cir) {
        if(!SquakeOptions.INSTANCE.fallDamageStop) {
            this.velocityDirty = false;
            this.velocityModified = false;
        }
    }
}
