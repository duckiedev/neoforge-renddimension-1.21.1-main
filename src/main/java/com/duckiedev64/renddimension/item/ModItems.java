package com.duckiedev64.renddimension.item;

import com.duckiedev64.renddimension.RendDimensionMod;
import com.duckiedev64.renddimension.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RendDimensionMod.MOD_ID);

    public static final DeferredItem<Item> RENDERPEARL = ITEMS.register("renderpearl",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EYEOFRENDER = ITEMS.register("eyeofrender",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RENDERMAN_SPAWN_EGG = ITEMS.register("renderman_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.RENDERMAN, 0x47080c, 0xe67e85,
                    new Item.Properties()));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
