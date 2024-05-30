package net.spaceeye.vmod.mixin;

import net.spaceeye.vmod.constraintsManaging.ShipDeletionFilter;
import net.spaceeye.vmod.constraintsManaging.VSConstraintsTracker;
import net.spaceeye.vmod.events.AVSEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.apigame.constraints.VSConstraint;
import org.valkyrienskies.core.impl.game.ships.ShipData;
import org.valkyrienskies.core.impl.game.ships.ShipObjectServerWorld;

import java.util.List;

@Mixin(ShipObjectServerWorld.class)
abstract public class ShipObjectServerWorldMixin {
    @Final
    @Shadow
    private List<ShipData> deletedShipObjects;

    @Inject(method = "deleteShip", at = @At(value = "HEAD"), remap = false, cancellable = true)
    void vmod$deleteShip(ServerShip ship, CallbackInfo ci) {
        ShipDeletionFilter.INSTANCE.filterShipsToDelete(ship, ci);
    }

    // it's called before they get deleted
    @Inject(method = "postTick", at = @At(value = "FIELD", target = "Lorg/valkyrienskies/core/impl/game/ships/ShipObjectServerWorld;deletedShipObjects:Ljava/util/List;"), remap = false)
    void vmod_postTickMixin(CallbackInfo ci) {
        if (deletedShipObjects == null) {return;}
        deletedShipObjects.forEach((data) -> AVSEvents.INSTANCE.getServerShipRemoveEvent().emit(new AVSEvents.ServerShipRemoveEvent(data)));
    }

    @Inject(method = "createNewConstraint", at = @At(value = "FIELD", target = "Lorg/valkyrienskies/core/impl/game/ships/ShipObjectServerWorld;constraints:Ljava/util/Map;"), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
    void vmod_createNewConstraints(VSConstraint vsConstraint, CallbackInfoReturnable<Integer> cir, int var4) {
        VSConstraintsTracker.INSTANCE.addNewConstraint(vsConstraint, var4);
    }

    @Inject(method = "removeConstraint", at = @At(value = "FIELD", target = "Lorg/valkyrienskies/core/impl/game/ships/ShipObjectServerWorld;constraintsDeletedThisTick:Ljava/util/List;"), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
    void vmod_removeConstraint(int constraintId, CallbackInfoReturnable<Boolean> cir, VSConstraint var2) {
        VSConstraintsTracker.INSTANCE.removeConstraint(var2, constraintId);
    }

    @Inject(method = "updateConstraint", at = @At(value = "FIELD", target = "Lorg/valkyrienskies/core/impl/game/ships/ShipObjectServerWorld;constraintsUpdatedThisTick:Ljava/util/List;"), remap = false)
    void vmod_updateConstraint(int constraintId, VSConstraint updatedVSConstraint, CallbackInfoReturnable<Boolean> cir) {
        VSConstraintsTracker.INSTANCE.updateConstraint(updatedVSConstraint, constraintId);
    }
}
