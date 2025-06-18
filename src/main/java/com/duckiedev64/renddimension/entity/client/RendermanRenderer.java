package com.duckiedev64.renddimension.entity.client;

import com.duckiedev64.renddimension.RendDimensionMod;
import com.duckiedev64.renddimension.entity.custom.RendermanEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;

public class RendermanRenderer extends EndermanRenderer {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RendDimensionMod.MOD_ID, "textures/entity/renderman.png");

    public RendermanRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(net.minecraft.world.entity.monster.EnderMan entity) {
        return TEXTURE;
    }
}
