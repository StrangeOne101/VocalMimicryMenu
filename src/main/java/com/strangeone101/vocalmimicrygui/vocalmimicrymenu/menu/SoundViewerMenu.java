package com.strangeone101.vocalmimicrygui.vocalmimicrymenu.menu;

import com.strangeone101.vocalmimicrygui.vocalmimicrymenu.SoundBreakdown;
import com.strangeone101.vocalmimicrygui.vocalmimicrymenu.VocalMimicryMenu;
import com.strangeone101.vocalmimicrygui.vocalmimicrymenu.resources.MenuBase;
import com.strangeone101.vocalmimicrygui.vocalmimicrymenu.resources.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SoundViewerMenu extends MenuBase {

    private static Map<Player, SoundViewerMenu> LAST_OPEN = new HashMap<>();

    private SoundBreakdown.SoundWrapper selected;
    private SoundBreakdown.SoundsContainer open;
    private int page;
    private SoundViewerMenu prev;
    private final SoundViewerMenu instance = this;

    public SoundViewerMenu(SoundBreakdown.SoundsContainer soundsContainer) {
        super(getTitle(soundsContainer), getRowSize(soundsContainer));

        this.open = soundsContainer;
    }

    public static int getRowSize(SoundBreakdown.SoundsContainer container) {
        int size = 1;

        if (container.list.size() <= 18) size += 2;
        else {
            int needed = (container.list.size() - 1) / 9 + 1;
            size += needed;
        }

        return Math.min(size, 6);
    }

    public static String getTitle(SoundBreakdown.SoundsContainer container) {
        if (container.parent == null) return "Pick a sound category";
        else return container.name + " Sounds";
    }

    public MenuItem getItemFor(SoundBreakdown.SoundWrapperBase soundWrapper) {

        MenuItem item = new SoundItem(soundWrapper);

        if (soundWrapper instanceof SoundBreakdown.SoundWrapper) {
            SoundBreakdown.SoundWrapper wrapper = (SoundBreakdown.SoundWrapper) soundWrapper;
            item.addDescription(ChatColor.GRAY + "Set the VocalMimicry sound to " + wrapper.sound.name());
            item.addDescription("");
            item.addDescription(ChatColor.YELLOW + "Click to preview the sound.");
            if (soundWrapper == selected) {
                item.addDescription(ChatColor.GREEN + ChatColor.BOLD.toString() + "Click again to set it!");
            } else {
                item.addDescription(ChatColor.YELLOW + "Click again to set it.");
            }
        } else if (soundWrapper instanceof SoundBreakdown.SoundsContainer) {
            SoundBreakdown.SoundsContainer container = (SoundBreakdown.SoundsContainer) soundWrapper;
            item.addDescription(ChatColor.GRAY + "Click to open " + container.name);
        }
        item.setEnchanted(soundWrapper == selected);
        return item;
    }

    public void update() {
        clearMenu();

        int startPoint = 0;

        int size = this.open.list.size();

        if (size == 1) startPoint = 13;
        else if (size == 2 || size == 3) startPoint = 12;
        else if (size == 4 || size == 5) startPoint = 11;
        else if (size == 6 || size == 7) startPoint = 10;

        int start = 45 * page;
        int end = Math.min(45 * (page + 1), size);
        /*for (int i = 0; i < this.open.list.size(); i++) {
            SoundBreakdown.SoundWrapperBase soundWrapper = this.open.list.get(i);
            MenuItem item = getItemFor(soundWrapper);
            this.addMenuItem(item, startPoint + i);
        }*/
        for (int i = start; i < end; i++) {
            SoundBreakdown.SoundWrapperBase soundWrapper = this.open.list.get(i);
            MenuItem item = getItemFor(soundWrapper);
            this.addMenuItem(item, startPoint + (i % 45));
        }

        if (page > 0) {
            this.addMenuItem(getPage(true), this.size - 9);
        } else {
            this.addMenuItem(getBack(), this.size - 9);
        }

        if (this.open.list.size() > 45 && page < (this.open.list.size() - 1) / 45) {
            this.addMenuItem(getPage(false), this.size - 1);
        }
    }

    public MenuItem getPage(boolean left) {
        String title = ChatColor.YELLOW + (left ? "Previous Page" : "Next Page");
        int current = page + 1;
        int max = (this.open.list.size() - 1) / 45 + 1;
        title = title + ChatColor.DARK_GRAY + " (" + ChatColor.YELLOW + current + ChatColor.DARK_GRAY + "/"
                + ChatColor.YELLOW + max + ChatColor.DARK_GRAY + ")";
        MenuItem item = new MenuItem(title, Material.ARROW, 1) {
            @Override
            public void onClick(Player player) {
                if (left) page--;
                else page++;

                update();
            }
        };
        item.addDescription(ChatColor.GRAY + "Click to go to the " + (left ? "previous" : "next") + " page.");
        return item;
    }

    public MenuItem getBack() {
        boolean close = this.open.parent == null;

        MenuItem item = new MenuItem(ChatColor.YELLOW + (close ? "Close the menu" : "Go back"), Material.ARROW, 1) {
            @Override
            public void onClick(Player player) {
                if (close) closeMenu(player);
                else {
                    SoundViewerMenu menu = instance.prev;
                    menu.openMenu(player);
                    menu.selected = selected;
                }
            }
        };

        if (close) item.addDescription(ChatColor.GRAY + "Click to close the menu");
        else item.addDescription(ChatColor.GRAY + "Click to go back to " + open.parent.name);

        return item;
    }

    @Override
    public void openMenu(Player player) {
        super.openMenu(player);
        update();

        LAST_OPEN.put(player, this);
    }

    public class SoundItem extends MenuItem {

        private SoundBreakdown.SoundWrapperBase base;

        public SoundItem(SoundBreakdown.SoundWrapperBase soundWrapper) {
            super(ChatColor.YELLOW + soundWrapper.name, soundWrapper.stack);

            this.base = soundWrapper;
        }

        @Override
        public void onClick(Player player) {
            if (this.base instanceof SoundBreakdown.SoundWrapper) {
                if (selected == this.base) {
                    VocalMimicryMenu.setVocalSound(player, ((SoundBreakdown.SoundWrapper) this.base).sound);
                    player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 2);
                    player.sendMessage(ChatColor.GREEN + "VocalMimicry sound set to " + ((SoundBreakdown.SoundWrapper) this.base).sound.name());
                    selected = null;
                } else {
                    if (selected != null) player.stopSound(selected.sound);
                    selected = (SoundBreakdown.SoundWrapper) this.base;
                    player.playSound(player.getLocation(), ((SoundBreakdown.SoundWrapper) this.base).sound, SoundCategory.MASTER,1, 1);
                }
                instance.update();
            } else {
                SoundViewerMenu menu = new SoundViewerMenu((SoundBreakdown.SoundsContainer) this.base);
                menu.openMenu(player);
                menu.selected = selected;
                menu.prev = instance;
            }
        }
    }

    public static void openForPlayer(Player player) {
        SoundViewerMenu menu;
        if (LAST_OPEN.containsKey(player)) {
            menu = LAST_OPEN.get(player);
        } else {
            menu = new SoundViewerMenu(SoundBreakdown.getInstance().BASE);
        }
        menu.openMenu(player);
    }

    public static void releaseMemory(Player player) {
        LAST_OPEN.remove(player);
    }
}
