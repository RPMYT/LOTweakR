package xyz.lilyflower.lilytweaks.mixin.lotr.travel;

import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.lilyflower.lilytweaks.util.config.lotr.LOTRTravelFeatureConfig;

@Mixin(LOTRWaypoint.class)
public class FastTravelWaypointOverrideController {
    @Inject(method = "hasPlayerUnlocked", at = @At("HEAD"), cancellable = true, remap = false)
    public void modifyUnlockStatus(EntityPlayer entityplayer, CallbackInfoReturnable<Boolean> cir) {
        if (LOTRTravelFeatureConfig.UNLOCK_WAYPOINTS) {
            cir.setReturnValue(true);
        }

        if (LOTRTravelFeatureConfig.isWaypointDisabled((LOTRWaypoint) (Object) this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isCompatibleAlignment", at = @At("HEAD"), cancellable = true, remap = false)
    public void noLocking(EntityPlayer entityplayer, CallbackInfoReturnable<Boolean> cir) {
        if (LOTRTravelFeatureConfig.NO_WAYPOINT_LOCKING) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isHidden", at = @At("HEAD"), cancellable = true, remap = false)
    public void disableWaypoint(CallbackInfoReturnable<Boolean> cir) {
        if (LOTRTravelFeatureConfig.isWaypointDisabled((LOTRWaypoint) (Object) this)) {
            cir.setReturnValue(true);
        }
    }
}
