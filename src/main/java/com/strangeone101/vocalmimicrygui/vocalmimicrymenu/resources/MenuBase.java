package com.strangeone101.vocalmimicrygui.vocalmimicrymenu.resources;

import java.util.HashMap;

import com.strangeone101.vocalmimicrygui.vocalmimicrymenu.VocalMimicryMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MenuBase implements InventoryHolder {

	protected HashMap<Integer, MenuItem> items = new HashMap<Integer, MenuItem>();
    protected Inventory inventory;
    protected String title;
    protected int size;
    protected int lastClickedSlot = -1;
    
    public ChatColor GRAY = ChatColor.GRAY;
    
    /**
     * Creates a new BaseMenu with the given title and number of rows of slots
     * for menu item.
     */
    public MenuBase(String title, int rows) {
        this.title = title;
        this.size = rows * 9;
    }

    /**Adds an item to the menu at the specified position*/
    public boolean addMenuItem(MenuItem item, int x, int y) {
        return addMenuItem(item, y * 9 + x);
    }

    /**Adds an item to the menu at the specified index.*/
    public boolean addMenuItem(MenuItem item, int index) {
        if (index < 0) { //So negative slots go in reverse order
            index = getInventory().getSize() - index;
        }
        ItemStack slot = getInventory().getItem(index);
        if (slot != null && slot.getType() != Material.AIR) {
            return false;
        }

        ItemStack stack = item.getItemStack();
        this.getInventory().setItem(index, stack);
        items.put(index, item);
        item.setMenu(this);
        return true;
    }
    
    public void setLastClickedSlot(int slot)
    {
    	this.lastClickedSlot = slot;
    }
    
    public int getLastClickedSlot() 
    {
		return lastClickedSlot;
	}

    public boolean removeMenuItem(int x, int y) {
        return removeMenuItem(y * 9 + x);
    }

    public boolean removeMenuItem(int index) {
        ItemStack slot = getInventory().getItem(index);
        if (slot == null || slot.getType() == Material.AIR) {
            return false;
        }
        getInventory().clear(index);
        items.remove(index);
        return true;
    }
    
    

    /**On click. Called by the main BendingGUI listener*/
    protected void selectMenuItem(Player player, int index) 
    {
        if (items.containsKey(index) && this.getInventory().getItem(index) != null) 
        {
            MenuItem item = items.get(index);
            item.onClick(player);
        }
    }
    
    protected MenuItem getMenuItem(int index)
    {
    	if (items.containsKey(index) && this.getInventory().getItem(index) != null) 
        {
            MenuItem item = items.get(index);
            return item;
        }
    	return null;
    }

    /**Opens a menu for a player. Don't overthink it*/
    public void openMenu(Player player) 
    {
        if (getInventory().getViewers().contains(player)) 
        {
            throw new IllegalArgumentException(player.getName() + " is already viewing " + player.getOpenInventory().getTitle());
        }
        player.openInventory(getInventory());
    }

    /** Closes a menu for a player. Shouldn't take a genius to figure this out.*/
    public void closeMenu(Player player) {
        if (getInventory().getViewers().contains(player)) {
            InventoryCloseEvent event = new InventoryCloseEvent(player.getOpenInventory());
            Bukkit.getPluginManager().callEvent(event);
            player.closeInventory();
            getInventory().getViewers().remove(player);
        }
    }

    public void clearMenu() {
        getInventory().clear();
        items.clear();
    }
    
    /**Closes this menu and opens another in the next tick. Avoids Bukkit glitchiness.
     * caused by closing and opening inventories in the same tick.*/
    public void switchMenu(final Player player, MenuBase toMenu) 
    {
    	final MenuBase menu = toMenu;
    	this.closeMenu(player);
        new BukkitRunnable() 
        {
            public void run() 
            {
                menu.openMenu(player);
            }
        }.runTask(VocalMimicryMenu.INSTANCE);
    }

    /**Gets the inventory used by this menu.*/
    public Inventory getInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(this, size, title);
        }
        return inventory;
    }

    @Override
    protected MenuBase clone() {
        MenuBase clone = new MenuBase(title, size);
        for (int index : items.keySet()) {
            addMenuItem(items.get(index), index);
        }
        return clone;
    }

    /**
     * Updates this menu after changes are made so that viewers can instantly
     * see them
     */
    /*public void updateMenu() {
        for (HumanEntity entity : getInventory().getViewers()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.updateInventory();
            }
        }
    }*/
}
