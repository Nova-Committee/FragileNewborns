package committee.nova.fragilenewborns.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Shadow
    public abstract boolean isBaby();

    @Redirect(method = "getMaxHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"))
    private double redirect$getMaxHealth(LivingEntity instance, Attribute attr) {
        return isBaby() ? .5 * instance.getAttributeValue(attr) : instance.getAttributeValue(attr);
    }
}
