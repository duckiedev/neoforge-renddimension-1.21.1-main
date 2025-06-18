package com.duckiedev64.renddimension.entity.custom;

import com.duckiedev64.renddimension.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.item.enchantment.Enchantments;

public class RendermanEntity extends EnderMan {
    public RendermanEntity(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D) // Different health
                .add(Attributes.MOVEMENT_SPEED, 0.35D) // Slightly faster
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override public boolean isSensitiveToWater() {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);

        // Same drop chance as Enderman (50% base + 3% per looting level)
        if (this.random.nextFloat() < 0.5F + (0.03F)) {
            this.spawnAtLocation(ModItems.RENDERPEARL.get());
        }
    }

}
