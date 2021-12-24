package com.github.iamtakagi.epfix;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderpearlGlitchFixer extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	private final ImmutableSet<Material> MATERIALS = Sets.immutableEnumSet(
			// 板ガラス
			Material.LEGACY_STAINED_GLASS_PANE,
			Material.GLASS_PANE,
			// フェンス
			Material.LEGACY_FENCE,
			Material.LEGACY_IRON_FENCE,
			Material.BIRCH_FENCE,
			Material.SPRUCE_FENCE,
			Material.CRIMSON_FENCE,
			Material.ACACIA_FENCE,
			Material.DARK_OAK_FENCE,
			Material.OAK_FENCE,
			Material.WARPED_FENCE,
			// 階段
			Material.LEGACY_WOOD_STAIRS,
			Material.LEGACY_SMOOTH_STAIRS,
			Material.LEGACY_WOOD_STAIRS,
			Material.LEGACY_BIRCH_WOOD_STAIRS,
			Material.ACACIA_STAIRS,
			Material.BRICK_STAIRS,
			Material.COBBLESTONE_STAIRS,
			Material.DARK_OAK_STAIRS,
			Material.NETHER_BRICK_STAIRS,
			Material.QUARTZ_STAIRS,
			Material.SANDSTONE_STAIRS,
			Material.ACACIA_STAIRS,
			Material.BRICK_STAIRS,
			Material.SMOOTH_RED_SANDSTONE_STAIRS,
			Material.STONE_STAIRS
	);

	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.hasItem())
				&& (event.getItem().getType() == Material.ENDER_PEARL)) {
			Block block = event.getClickedBlock();
			if ((block.getType().isSolid()) && (!(block.getState() instanceof InventoryHolder))) {
				event.setCancelled(true);
				Player player = event.getPlayer();
				player.setItemInHand(event.getItem());
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
			Location to = event.getTo();
			if (this.MATERIALS.contains(to.getBlock().getType())) {
				event.setCancelled(true);
				return;
			}
			to.setX(to.getBlockX() + 0.5D);
			to.setZ(to.getBlockZ() + 0.5D);
			event.setTo(to);
		}
	}
}