package com.strangeone101.vocalmimicrymenu.menu;

import com.strangeone101.vocalmimicrymenu.PlayerCache;
import com.strangeone101.vocalmimicrymenu.VocalMimicryMenu;
import com.strangeone101.vocalmimicrymenu.resources.EmptyMenuItem;
import com.strangeone101.vocalmimicrymenu.resources.MenuBase;
import com.strangeone101.vocalmimicrymenu.resources.MenuItem;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapper;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapperBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FavoritesMenu extends MenuBase {

    private Player player;
    private PlayerCache cache;
    private SoundWrapper selected;
    private SoundViewerMenu prev;
    private final FavoritesMenu instance = this;
    private List<SoundWrapper> favs = new ArrayList<>();

    public FavoritesMenu(SoundViewerMenu menu) {
        super("Favorited Sounds", 3);

        this.prev = menu;
    }

    public void update() {
        clearMenu();

        addMenuItem(getBack(), 18);
        addMenuItem(getInfo(), 26);


        if (favs.size() == 0) {
            addMenuItem(getNone(), 13);
        } else {
            int size = favs.size();
            /*int startPoint = 13;
            if (size == 1) startPoint = 13;
            else if (size == 2 || size == 3) startPoint = 12;
            else if (size == 4 || size == 5) startPoint = 11;
            else if (size == 6 || size == 7) startPoint = 10;*/

            for (int i = 0; i < size; i++) {
                SoundWrapper soundWrapper = favs.get(i);
                MenuItem item = getItemFor(soundWrapper);
                this.addMenuItem(item, getIndex(i, size));
            }
        }
    }

    private MenuItem getItemFor(SoundWrapper soundWrapper) {
        boolean set = cache.getSetSound() != null && cache.getSetSound() == soundWrapper.sound;
        boolean fav = cache.isFavorite(soundWrapper);
        String name = (fav ? ChatColor.YELLOW : ChatColor.RED) + soundWrapper.name;
        ItemStack stack = soundWrapper.stack;
        //We use the parent stack here so it better represents what it is. But only if it is a mob, block or item
        if (!soundWrapper.path.startsWith("music") && !soundWrapper.path.startsWith("other")) stack = soundWrapper.parent.stack;

        MenuItem item = new MenuItem(name, stack) {
            @Override
            public void onClick(Player player) {
                if (this.isRightClicked) {
                    boolean fav = cache.isFavorite(soundWrapper);
                    if (fav) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 1, 0.5F);
                        cache.removeFavorite(soundWrapper);
                        player.sendMessage(ChatColor.YELLOW + "You unfavorited the sound " + soundWrapper.sound.name() + ".");

                    } else if (cache.canFavorite()) { //Only do this if they have permission to favorite
                        if (cache.addFavorite(soundWrapper)) {
                            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 1, 2F);
                            player.sendMessage(ChatColor.GREEN + "You readded sound " + soundWrapper.sound.name() + " to your favorites!");
                        } else {
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1, 0.6F);
                            player.sendMessage(ChatColor.RED + "You can only add " + cache.getMaxFavorites() + " favorites!");
                        }
                    }
                    instance.update();
                    return;
                }

                if (fav) { //Don't bother allowing them to set it if they unfavorited it
                    if (selected == soundWrapper) { //If the item is selected
                        if (VocalMimicryMenu.setVocalSound(player, soundWrapper.sound)) {
                            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 1, 2);
                            player.sendMessage(ChatColor.GREEN + "VocalMimicry sound set to " + soundWrapper.sound.name());
                            cache.setSetSound(soundWrapper.sound);
                        } else { //It failed to set. Usually because its blacklisted
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1, 0.6F);
                            player.sendMessage(ChatColor.RED + "You are not allowed to use the sound " + soundWrapper.sound.name() + "!");
                        }
                        selected = null;
                    } else { //They are clicking it for the first time. So preview the sound by playing it on master
                        if (selected != null) player.stopSound(selected.sound);
                        selected = soundWrapper;
                        player.playSound(player.getLocation(), soundWrapper.sound, SoundCategory.MASTER,1, 1);
                    }

                    instance.update();
                }
            }
        };
        item.addDescription(ChatColor.GRAY + "Set the VocalMimicry sound to " + soundWrapper.sound.name());
        item.addDescription("");

        if (fav) {
            item.addDescription(ChatColor.YELLOW + "Click to preview the sound.");
            if (soundWrapper == selected) {
                item.addDescription(ChatColor.GREEN + ChatColor.BOLD.toString() + "Click again to set it!");
            } else {
                item.addDescription(ChatColor.YELLOW + "Click again to set it.");
            }

            item.addDescription(ChatColor.YELLOW + "Right click to " + (fav ? "remove from" : "add to") + " your favorites.");

            if (set) {
                item.addDescription("");
                item.addDescription(ChatColor.GREEN + ChatColor.BOLD.toString() + "Currently set as active sound!");
            }
        } else {
            item.addDescription(ChatColor.RED + "You have removed this from your favorites.");
            item.addDescription(ChatColor.RED + "This will disappear next time you open this");
            item.addDescription(ChatColor.RED + "menu. You can still re-add it by right clicking.");
        }

        item.setEnchanted(fav && selected == soundWrapper);

        return item;

    }

    public MenuItem getBack() {

        MenuItem item = new MenuItem(ChatColor.YELLOW + "Go back", Material.ARROW, 1) {
            @Override
            public void onClick(Player player) {
                SoundViewerMenu menu = instance.prev;
                menu.openMenu(player);

            }
        };

        item.addDescription(ChatColor.GRAY + "Click to go back to the standard sound menu");

        return item;
    }

    public MenuItem getInfo() {
        MenuItem item = new EmptyMenuItem(ChatColor.YELLOW + "Information", Material.OAK_SIGN, 1);

        item.addDescription(ChatColor.GRAY + "Favorited sounds are listed here. Sounds can be");
        item.addDescription(ChatColor.GRAY + "favorited by right clicking the sound. You can only");
        item.addDescription(ChatColor.GRAY + "have a maximum of " + cache.getMaxFavorites() + " favorites at once.");

        return item;
    }

    public MenuItem getNone() {
        MenuItem item = new EmptyMenuItem(ChatColor.GRAY + "Nothing is here :(", Material.MAP, 1);
        item.addDescription(ChatColor.DARK_GRAY + "You don't have any favorites yet!");
        return item;
    }

    public int getIndex(int number, int max) {
        if (max <= 7) return (13 - (max / 2)) + number;
        if (max <= 9) return 3 + (((number / 3) * 9) + (number % 3));
        if (max <= 15) return 2 + (((number / 5) * 9) + (number % 5));
        if (max <= 21) return 1 + (((number / 7) * 9) + (number % 7));
        return number;
    }

    @Override
    public void openMenu(Player player) {
        super.openMenu(player);
        this.player = player;
        this.cache = PlayerCache.getCache(player);
        this.favs = cache.getFavorites();
        cache.recalculateMax();
        update();
    }
}
