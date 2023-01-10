package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.init.EntityInit;
import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import at.kaindorf.arcane_conjouring.item.wand.addon.SpellRingItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MagicBolt extends AbstractHurtingProjectile {

    private ItemStack ring;


    public MagicBolt(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public MagicBolt(Level level, LivingEntity entity, ItemStack ring) {
        this(EntityInit.MAGIC_BOLT.get(), level);
        this.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
        this.setOwner(entity);
        this.ring = ring;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (ring != null) {

            SpellRingItem.apply(ring, new CastingTarget(hitResult.getBlockPos(), level));
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);

        if (hitResult.getEntity() instanceof LivingEntity entity && ring != null)
            SpellRingItem.apply(ring, new CastingTarget(entity));
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();

            this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double)f));
            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }

    public boolean isPickable() {
        return false;
    }
    protected boolean shouldBurn() {
        return false;
    }
    public boolean hurt(DamageSource p_36910_, float p_36911_) {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    protected float getInertia() {
        return 1.0F;
    }


}
