package com.strangeone101.vocalmimicrymenu.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class MenuItem{

    protected boolean isRightClicked;
    protected List<String> lore = new ArrayList<String>();
	protected MenuBase menu;
	protected int number;
	protected Material icon;
	protected String text;
	protected boolean isShiftClicked = false;
	protected boolean isEnchanted = false;

	protected ItemStack prebuiltStack;
	
	public MenuItem(String text, Material icon, int number) {
		this.text = text;
        this.icon = icon;
        this.number = number;
	}

	public MenuItem(String text, ItemStack stack) {
		this.prebuiltStack = stack.clone();
		this.number = 1;
		this.text = text;
	}
	
	public MenuItem(String string, Material icon) {
		this(string, icon, 1);
	}
	
	public void setEnchanted(boolean bool) {
		this.isEnchanted = bool;
	}
	
	public MenuBase getMenu() {
        return menu;
    }

    public int getNumber() {
        return number;
    }

    public Material getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

	public ItemStack getItemStack() {
		ItemStack slot = prebuiltStack == null ? new ItemStack(getIcon(), getNumber()) : prebuiltStack;
        ItemMeta meta = slot.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(getText());
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //So weapons don't show damage, etc. Kinda pointless for UIs
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); //So effects show on water bottles

		if (isEnchanted) {
			meta.addEnchant(Enchantment.LUCK, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		} else {

		}

        slot.setItemMeta(meta);

        return slot;
	}

	/***DO NOT USE PLAYER VARIABLE IF USING MenuBendingOptions! Use the class' player variable instead! This causes
	 * problems when looking at other player's menus. Called when a player clicks on the item.
	 * @param player The player clicking*/
	public abstract void onClick(Player player);
	
	public void setDescriptions(List<String> lines) {
		this.lore = lines;
	}
	
	public void addDescription(String line) {
		Collections.addAll(this.lore, line.split("\\n"));
	}
	
	public void setMenu(MenuBase menu) {
		this.menu = menu;
	}
	
	public boolean isShiftClicked() {
		return this.isShiftClicked;
	}
}
