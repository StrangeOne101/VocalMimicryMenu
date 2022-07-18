package com.strangeone101.vocalmimicrymenu.resources;


import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.command.PKCommand;
import com.strangeone101.vocalmimicrymenu.VocalMimicryMenu;
import com.strangeone101.vocalmimicrymenu.menu.SoundViewerMenu;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements org.bukkit.event.Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onMenuItemClicked(InventoryClickEvent event) {
		try {
			Inventory inventory = event.getInventory();
	        if (inventory.getHolder() instanceof MenuBase) {
	            MenuBase menu = (MenuBase) inventory.getHolder();
	            if (event.getWhoClicked() instanceof Player) {
	                Player player = (Player) event.getWhoClicked();
	                if (event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
	                	int index = event.getRawSlot();
	                    if (index < inventory.getSize()) {
	                    	if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
	                    		event.setCancelled(true);
	                    		menu.closeMenu(player);
	                    		player.sendMessage(ChatColor.RED + "You cannot modify the menu!");
	                    	}
	                    	MenuItem item = menu.getMenuItem(index);
	                    	if (item != null)
	                    	{
	                    		item.isShiftClicked = event.isShiftClick();
								item.isRightClicked = event.isRightClick();
	                    		item.onClick(player);
	                    		event.setCancelled(true);
	                    	}
	                    }
	                    else {
	                    	if (event.isShiftClick()) {
	                    		event.setCancelled(true);
	                    	}
	                    	menu.setLastClickedSlot(index);
	                    }
	                }
	            }
	        }
		}
		catch (Exception e) {
			event.getWhoClicked().closeInventory();
			event.getWhoClicked().sendMessage(ChatColor.RED + "There was an error processing the click event. Please contact your admin.");
			e.printStackTrace();
		}
    }
}
