package committee.nova.fragilenewborns.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Unique
    private byte fragilenewborns$babyCheckInterval;

    @Unique
    private boolean fragilenewborns$wasBaby = isBaby();

    @Shadow
    public abstract boolean isBaby();

    @Shadow
    public abstract void setHealth(float p_21154_);

    @Shadow
    public abstract float getHealth();

    @Redirect(method = "getMaxHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/entity/ai/attributes/Attribute;)D"))
    private double redirect$getMaxHealth(LivingEntity instance, Attribute attr) {
        return isBaby() ? .5 * instance.getAttributeValue(attr) : instance.getAttributeValue(attr);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void inject$tick(CallbackInfo ci) {
        if (fragilenewborns$wasBaby && fragilenewborns$check() && !isBaby()) {
            fragilenewborns$wasBaby = false;
            setHealth(2 * getHealth());
        }
    }

    @Unique
    private boolean fragilenewborns$check() {
        fragilenewborns$babyCheckInterval++;
        fragilenewborns$babyCheckInterval &= 19; // i %= 20
        return fragilenewborns$babyCheckInterval == 0;
    }
}
