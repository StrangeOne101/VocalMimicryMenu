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

public class Listener implements org.bukkit.event.Listener {

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

	@EventHandler
	public void playerLeave(PlayerQuitEvent event) {
		SoundViewerMenu.releaseMemory(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final String[] splits = event.getMessage().toLowerCase().split(" ");
		if (splits.length != 3) return;
		final Player player = event.getPlayer();
		Bukkit.getScheduler().runTaskLater(VocalMimicryMenu.INSTANCE, () -> {
			for (String s : Commands.commandaliases) {
				if (splits[0].equals("/" + s)) {
					for (String s1 : PKCommand.instances.get("help").getAliases()) {
						if (splits[1].equals(s1) && splits[2].equalsIgnoreCase("VocalMimicry")) {
							String string = ChatColor.YELLOW + "Want to set the sound of VocalMimicry easily?";
							player.sendMessage(string);

							TextComponent here = new TextComponent("here");
							here.setColor(ChatColor.GREEN);
							here.setBold(true);
							here.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.YELLOW + "Open the VocalMimicry menu!\n\n"
									+ ChatColor.YELLOW + "Run Command: " + ChatColor.GRAY + "/vocalmenu")));
							here.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vocalmenu"));

							TextComponent full = new TextComponent("Click ");
							full.setColor(ChatColor.YELLOW);

							TextComponent full2 = new TextComponent(" to open the VocalMimicry menu! Or run the command ");
							full2.setColor(ChatColor.YELLOW);
							full2.setBold(false);

							TextComponent full3 = new TextComponent("/vocalmenu");
							full3.setColor(ChatColor.RED);

							full2.addExtra(full3);
							here.addExtra(full2);
							full.addExtra(here);

							player.spigot().sendMessage(full);

						}
					}
				}
			}
		}, 1L);
	}
}
