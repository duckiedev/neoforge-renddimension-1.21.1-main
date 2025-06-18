package com.duckiedev64.renddimension.entity;

import com.duckiedev64.renddimension.RendDimensionMod;
import com.duckiedev64.renddimension.entity.custom.RendermanEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, RendDimensionMod.MOD_ID);

    public static final Supplier<EntityType<RendermanEntity>> RENDERMAN =
            ENTITY_TYPES.register("renderman", () -> EntityType.Builder.of(RendermanEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 2.9f) // Same size as Enderman
                    .build("renderman"));
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
