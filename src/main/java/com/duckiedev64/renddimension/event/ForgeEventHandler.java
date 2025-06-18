package com.duckiedev64.renddimension.event;

import com.duckiedev64.renddimension.RendDimensionMod;
import com.duckiedev64.renddimension.entity.custom.RendermanEntity;
import com.duckiedev64.renddimension.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = RendDimensionMod.MOD_ID)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof EnderMan enderman && !enderman.level().isClientSide()) {

            System.out.println("Enderman hurt! Damage type: " + event.getSource().getMsgId());
            System.out.println("Damage amount: " + event.getAmount());

            // Check for any magic damage (including splash potions)
            if (event.getSource().is(net.minecraft.world.damagesource.DamageTypes.INDIRECT_MAGIC) ||
                    event.getSource().is(net.minecraft.world.damagesource.DamageTypes.MAGIC)) {
                System.out.println("Magic damage detected - transforming!");
                transformToRenderman(enderman);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof EnderMan enderman) {
            // Prevent teleportation if recently hit by magic damage
            if (enderman.hurtTime > 0) {
                System.out.println("Preventing enderman teleport during transformation");
                event.setCanceled(true);
            }
        }
    }

    private static boolean isHarmingPotion(net.minecraft.world.item.ItemStack stack) {
        if (stack.getItem() == net.minecraft.world.item.Items.SPLASH_POTION ||
                stack.getItem() == net.minecraft.world.item.Items.LINGERING_POTION) {

            var potionContents = stack.get(net.minecraft.core.component.DataComponents.POTION_CONTENTS);
            if (potionContents != null) {
                for (var effect : potionContents.getAllEffects()) {
                    if (effect.getEffect() == MobEffects.HARM) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void transformToRenderman(EnderMan enderman) {
        Level level = enderman.level();
        BlockPos pos = enderman.blockPosition();

        RendermanEntity renderman = new RendermanEntity(ModEntities.RENDERMAN.get(), level);
        renderman.setPos(enderman.getX(), enderman.getY(), enderman.getZ());
        renderman.setYRot(enderman.getYRot());
        renderman.setHealth(enderman.getHealth());

        enderman.discard();
        level.addFreshEntity(renderman);

        level.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 0.5F);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    20, 0.5, 1.0, 0.5, 0.1);
        }
    }
}