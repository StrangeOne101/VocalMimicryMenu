package com.strangeone101.vocalmimicrymenu.menu;

import com.strangeone101.vocalmimicrymenu.PlayerCache;
import com.strangeone101.vocalmimicrymenu.SoundBreakdown;
import com.strangeone101.vocalmimicrymenu.VocalMimicryMenu;
import com.strangeone101.vocalmimicrymenu.resources.MenuBase;
import com.strangeone101.vocalmimicrymenu.resources.MenuItem;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapper;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapperBase;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundsContainer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class SoundViewerMenu extends MenuBase {

    public static final char STAR = '\u2B50';

    private SoundWrapper selected;
    private SoundsContainer open;
    private int page;
    private SoundViewerMenu prev;
    private final SoundViewerMenu instance = this;
    private Player player;
    private PlayerCache cache;

    public SoundViewerMenu(SoundsContainer soundsContainer) {
        super(getTitle(soundsContainer), getRowSize(soundsContainer));

        this.open = soundsContainer;
    }

    public static int getRowSize(SoundsContainer container) {
        int size = 1;

        if (container.list.size() <= 18) size += 2;
        else {
            int needed = (container.list.size() - 1) / 9 + 1;
            size += needed;
        }

        return Math.min(size, 6);
    }

    public static String getTitle(SoundsContainer container) {
        if (container.parent == null) return "Pick a sound category";
        else return container.name + " Sounds";
    }

    public MenuItem getItemFor(SoundWrapperBase soundWrapper) {
        MenuItem item = new SoundItem(soundWrapper);
        boolean set = cache.getSetSound() != null && soundWrapper instanceof SoundWrapper && cache.getSetSound() == (((SoundWrapper) soundWrapper).sound);
        boolean fav = cache.isFavorite(soundWrapper);

        if (soundWrapper instanceof SoundWrapper) {
            SoundWrapper wrapper = (SoundWrapper) soundWrapper;
            item.addDescription(ChatColor.GRAY + "Set the VocalMimicry sound to " + wrapper.sound.name());
            item.addDescription("");
            item.addDescription(ChatColor.YELLOW + "Click to preview the sound.");
            if (soundWrapper == selected) {
                item.addDescription(ChatColor.GREEN + ChatColor.BOLD.toString() + "Click again to set it!");
            } else {
                item.addDescription(ChatColor.YELLOW + "Click again to set it.");
            }
            if (cache.canFavorite() || fav) item.addDescription(ChatColor.YELLOW + "Right click to " + (fav ? "remove from" : "add to") + " your favorites.");

            if (set) {
                item.addDescription("");
                item.addDescription(ChatColor.GREEN + ChatColor.BOLD.toString() + "Currently set as active sound!");
            } else if (fav) {
                item.addDescription("");
                item.addDescription(ChatColor.GOLD + ChatColor.BOLD.toString() + "Set as a favorite!");
            }
        } else if (soundWrapper instanceof SoundsContainer) {
            SoundsContainer container = (SoundsContainer) soundWrapper;
            String name = container.name.toLowerCase();
            if (name.endsWith("s")) name = name.substring(0, name.length() - 1);
            if (container.path.contains(".")) name = name + " sounds";
            else name = "the " + name;
            item.addDescription(ChatColor.GRAY + "Click to open " + name);
        }
        item.setEnchanted(soundWrapper == selected);
        return item;
    }

    public void update() {
        clearMenu();

        int startPoint = 0;

        int size = this.open.list.size();
        int maxPage = (this.open.list.size() - 1) / 45;

        if (size == 1) startPoint = 13;
        else if (size == 2 || size == 3) startPoint = 12;
        else if (size == 4 || size == 5) startPoint = 11;
        else if (size == 6 || size == 7) startPoint = 10;

        int start = 45 * page; //The start index
        int end = Math.min(45 * (page + 1), size); //The finish index

        for (int i = start; i < end; i++) {
            SoundWrapperBase soundWrapper = this.open.list.get(i);
            MenuItem item = getItemFor(soundWrapper);
            this.addMenuItem(item, startPoint + (i % 45));
        }

        if (page > 0) {
            this.addMenuItem(getPage(true), this.size - 9);
        } else {
            this.addMenuItem(getBack(), this.size - 9);
        }

        if (this.open.list.size() > 45 && page < maxPage) {
            this.addMenuItem(getPage(false), this.size - 1);
        }

        //We check if the first item is a sound and not a container. Don't show while still in a category
        //but the base categories are fine
        if (cache.canFavorite() && (this.open.parent == null || this.open.list.get(0) instanceof SoundWrapper)) {
            int favIndex = this.size - 1;
            if (maxPage > 0) favIndex = this.size - 4;
            this.addMenuItem(getFavoriteMenu(), favIndex);
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
                if (this.isShiftClicked) { //Go to last page on either side
                    if (left) page = 0;
                    else page = max - 1;
                } else { //Just go one page
                    if (left) page--;
                    else page++;
                }

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

    public MenuItem getFavoriteMenu() {
        MenuItem item = new MenuItem(ChatColor.YELLOW + "Favorites", Material.GOLD_INGOT, 1) {
            @Override
            public void onClick(Player player) {
                FavoritesMenu menu = new FavoritesMenu(instance);
                menu.openMenu(player);
            }
        };
        item.addDescription(ChatColor.GRAY + "Click to open your favorites.");
        return item;
    }

    @Override
    public void openMenu(Player player) {
        super.openMenu(player);
        this.player = player;
        this.cache = PlayerCache.getCache(player);
        update();

        cache.setLastOpen(this);
    }

    public class SoundItem extends MenuItem {

        private SoundWrapperBase base;

        public SoundItem(SoundWrapperBase soundWrapper) {
            super((cache.isFavorite(soundWrapper) ? ChatColor.GOLD : ChatColor.YELLOW) + soundWrapper.name +
                    (cache.isFavorite(soundWrapper) ? " " + ChatColor.BOLD + STAR : ""), soundWrapper.stack);

            this.base = soundWrapper;
        }

        @Override
        public void onClick(Player player) {
            if (this.base instanceof SoundWrapper) {
                if (this.isRightClicked) { //They are trying to favorite it
                    boolean fav = cache.isFavorite(this.base);
                    if (fav) { //Its already a favorite, so unfavorite it
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 1, 0.5F);
                        cache.removeFavorite((SoundWrapper) this.base);
                        player.sendMessage(ChatColor.YELLOW + "You unfavorited the sound " + ((SoundWrapper) this.base).sound.name() + ".");

                    } else if (cache.canFavorite()) { //Only do this if they have permission to favorite
                        if (cache.addFavorite((SoundWrapper) this.base)) {
                            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 1, 2F);
                            player.sendMessage(ChatColor.GREEN + "Sound " + ((SoundWrapper) this.base).sound.name() + " added to favorites!");
                        } else {
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 0.6F);
                            player.sendMessage(ChatColor.RED + "You can only add " + cache.getMaxFavorites() + " favorites!");
                        }
                    }
                    instance.update();
                    return;
                }
                if (selected == this.base) { //If the sound is selected
                    if (VocalMimicryMenu.setVocalSound(player, ((SoundWrapper) this.base).sound)) { //Try set the sound
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 1, 2);
                        player.sendMessage(ChatColor.GREEN + "VocalMimicry sound set to " + ((SoundWrapper) this.base).sound.name());
                        cache.setSetSound(((SoundWrapper) this.base).sound);
                    } else { //It failed, usually because its blacklisted
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1, 0.6F);
                        player.sendMessage(ChatColor.RED + "You are not allowed to use the sound " + ((SoundWrapper) this.base).sound.name() + "!");
                    }
                    selected = null;
                } else { //They are clicking this for the first time, so preview the sound
                    if (selected != null) player.stopSound(selected.sound);
                    selected = (SoundWrapper) this.base;
                    player.playSound(player.getLocation(), ((SoundWrapper) this.base).sound, SoundCategory.MASTER,1, 1);
                }
                instance.update();
            } else { //If they clicked a category
                SoundViewerMenu menu = new SoundViewerMenu((SoundsContainer) this.base);
                menu.openMenu(player);
                menu.selected = selected;
                menu.prev = instance;
            }
        }
    }

    public static void openForPlayer(Player player) {
        MenuBase menu;
        PlayerCache cache = PlayerCache.getCache(player);
        cache.recalculateMax();
        if (cache.getLastOpen() != null) {
            menu = cache.getLastOpen();
        } else {
            menu = new SoundViewerMenu(SoundBreakdown.getInstance().BASE);
        }
        menu.openMenu(player);
    }
}
