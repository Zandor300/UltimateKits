package com.zandor300.ultimatekits.inventories;

import com.zandor300.ultimatekits.UltimateKits;
import com.zandor300.ultimatekits.enums.Kit;
import com.zandor300.zsutilities.inventorysystem.Inventory;
import com.zandor300.zsutilities.inventorysystem.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

/**
 * Created by Zandor on 3/23/15.
 */
public class KitMenu implements Listener {

	private static HashMap<String, BukkitTask> playerTask = new HashMap<String, BukkitTask>();

	public static void open(final Player player) {
		final Inventory inventory = new Inventory(ChatColor.DARK_PURPLE + "UltimateKits", 54);
		playerTask.put(player.getName(), Bukkit.getScheduler().runTaskTimer(UltimateKits.getPlugin(), new Runnable() {
			@Override
			public void run() {
				int i = 0;
				for(Kit kit : Kit.getAllKits()) {
					if(kit.getPlayerCooldown().containsKey(player.getName())) {
						Item item = new Item(Material.STAINED_GLASS_PANE, 1, ChatColor.RED + kit.getName(), (byte) 14);
						item.setLore("",
								ChatColor.GREEN + "Cooldown will expire in " +
										kit.getPlayerCooldown().get(player.getName()) + "seconds");
						inventory.setItem(i, item);
					} else {
						Item item = new Item(Material.STAINED_GLASS_PANE, 1, ChatColor.GREEN + kit.getName(), (byte) 13);
						inventory.setItem(i, item);
					}
					i++;
				}
			}
		}, 0l, 20l));
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!event.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "UltimateKits"))
			return;

		BukkitTask task = playerTask.get(event.getPlayer().getName());
		if (task != null) {
			task.cancel();
			playerTask.remove(event.getPlayer().getName());
		}
	}
}