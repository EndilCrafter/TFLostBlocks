package kittehmod.tflostblocks.client;

import kittehmod.tflostblocks.blockentities.ModBlockEntities;
import kittehmod.tflostblocks.blocks.ModBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.block.AuroraBrickBlock;
import twilightforest.block.TFBlocks;

import java.awt.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler
{
	
	public static final ModelLayerLocation TOWERWOOD_SIGN = new ModelLayerLocation(new ResourceLocation("tflostblocks", "towerwood_sign"), "main");
	
	public static void setupRenderers() {
		BlockEntityRenderers.register(ModBlockEntities.LOST_TF_SIGN.get(), SignRenderer::new);
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.AURORALIZED_GLASS_PANE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.TOWERWOOD_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.TOWERWOOD_TRAPDOOR.get(), RenderType.cutout());
	}
	
	@SubscribeEvent
	public static void registerBlockColours(ColorHandlerEvent.Block event) {
		BlockColors blockColours = event.getBlockColors();
		// Tint Towerwood
		blockColours.register((state, worldIn, pos, tintIndex) -> {
		if (tintIndex > 15) return 0xFFFFFF;

		if (worldIn == null || pos == null) {
			return -1;
		} else {
			float f = AuroraBrickBlock.rippleFractialNoise(2, 32.0f, pos, 0.4f, 1.0f, 2f);
			return Color.HSBtoRGB(0.1f, 1f - f, (f + 2f) / 3f);
		}
		}, ModBlocks.TOWERWOOD_STAIRS.get(), ModBlocks.TOWERWOOD_SLAB.get(), ModBlocks.TOWERWOOD_FENCE.get(), ModBlocks.TOWERWOOD_FENCE_GATE.get(), ModBlocks.TOWERWOOD_DOOR.get(), ModBlocks.TOWERWOOD_TRAPDOOR.get(), ModBlocks.TOWERWOOD_BUTTON.get(), ModBlocks.TOWERWOOD_PRESSURE_PLATE.get(), ModBlocks.MOSSY_TOWERWOOD_STAIRS.get(), ModBlocks.MOSSY_TOWERWOOD_SLAB.get());
		// Tint Aurora Stairs & Slabs
		blockColours.register((state, worldIn, pos, tintIndex) -> {
			if (tintIndex > 15) return 0xFFFFFF;

			int normalColor = blockColours.getColor(TFBlocks.AURORA_BLOCK.get().defaultBlockState(), worldIn, pos, tintIndex);

			int red = (normalColor >> 16) & 255;
			int blue = normalColor & 255;
			int green = (normalColor >> 8) & 255;

			float[] hsb = Color.RGBtoHSB(red, blue, green, null);

			return Color.HSBtoRGB(hsb[0], hsb[1] * 0.5F, Math.min(hsb[2] + 0.4F, 0.9F));
		}, ModBlocks.AURORA_STAIRS.get(), ModBlocks.AURORA_WALL.get(), ModBlocks.AURORALIZED_GLASS_PANE.get());
	}
	
	@SubscribeEvent
	public static void registerItemColours(ColorHandlerEvent.Item event) {
		BlockColors blockColours = event.getBlockColors();
		ItemColors itemColours = event.getItemColors();
		itemColours.register((stack, tintIndex) -> blockColours.getColor(((BlockItem)stack.getItem()).getBlock().defaultBlockState(), null, null, tintIndex), ModBlocks.AURORA_STAIRS.get(), ModBlocks.AURORA_WALL.get(), ModBlocks.AURORALIZED_GLASS_PANE.get());
	}
}
