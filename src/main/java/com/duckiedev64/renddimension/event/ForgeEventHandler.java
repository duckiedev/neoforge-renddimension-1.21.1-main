package com.duckiedev64.renddimension.event;

import com.duckiedev64.renddimension.RendDimensionMod;
import com.duckiedev64.renddimension.entity.ModEntities;
import com.duckiedev64.renddimension.entity.custom.RendermanEntity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.core.component.DataComponents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.minecraft.world.phys.EntityHitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventBusSubscriber(modid = RendDimensionMod.MOD_ID)
public class ForgeEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgeEventHandler.class);

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof ThrownPotion potion && event.getRayTraceResult() instanceof EntityHitResult entityHit) {
            Entity hitEntity = entityHit.getEntity();
            LOGGER.info("Potion hit entity: {}", hitEntity.getClass().getSimpleName());

            if (hitEntity instanceof EnderMan enderman) {
                LOGGER.info("Potion directly hit Enderman");

                PotionContents potionContents = potion.getItem().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                LOGGER.info("Potion type: {}", potionContents.potion().isPresent() ? potionContents.potion().get() : "Unknown");

                if (potionContents.is(Potions.HARMING) || potionContents.is(Potions.STRONG_HARMING)) {
                    LOGGER.info("Harming potion - transforming Enderman");

                    if (!enderman.level().isClientSide) {
                        Level level = enderman.level();
                        double x = enderman.getX();
                        double y = enderman.getY();
                        double z = enderman.getZ();

                        RendermanEntity renderman = ModEntities.RENDERMAN.get().create(level);
                        if (renderman != null) {
                            renderman.moveTo(x, y, z, enderman.getYRot(), enderman.getXRot());
                            renderman.setNoAi(enderman.isNoAi());
                            level.addFreshEntity(renderman);
                            LOGGER.info("Renderman spawned");
                        }

                        enderman.discard();
                        LOGGER.info("Enderman removed");
                    }
                }
            }
        }
    }
}