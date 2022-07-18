package com.strangeone101.vocalmimicrymenu;

import com.strangeone101.vocalmimicrymenu.resources.MenuBase;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapper;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapperBase;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerCache {

    private static Map<Player, PlayerCache> CACHE = new HashMap<>();

    public static final int HARDCODED_MAX_FAVORITES = 9;

    private Player player;
    private MenuBase lastOpen;
    private Sound setSound;
    private int maxFavorites;
    private List<SoundWrapper> favorites = new ArrayList<>();

    PlayerCache(Player player) {
        this.player = player;
        this.setSound = Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO; //The default sound

        //Load the favorites
        Bukkit.getScheduler().runTaskAsynchronously(VocalMimicryMenu.INSTANCE, () -> {
            Arrays.stream(DatabaseUtil.getFavorites(player)).forEach(sound -> {
                SoundWrapperBase wrapperBase = SoundBreakdown.INSTANCE.SOUND_ENTRIES.get(sound);
                if (wrapperBase instanceof SoundWrapper) favorites.add((SoundWrapper) wrapperBase);
            });
        });

        recalculateMax();

        CACHE.put(player, this);
    }

    public static PlayerCache getCache(Player player) {
        if (CACHE.containsKey(player)) return CACHE.get(player);

        return new PlayerCache(player);
    }

    public static void release(Player player) {
        UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskLater(VocalMimicryMenu.INSTANCE, () -> {
            if (!Bukkit.getOfflinePlayer(uuid).isOnline()) CACHE.remove(player);
        }, 20 * 10); //Wait 10 seconds before releasing cache
    }

    public boolean isFavorite(SoundWrapper wrapper) {
        return favorites.contains(wrapper);
    }

    public boolean isFavorite(SoundWrapperBase wrapper) {
        return wrapper instanceof SoundWrapper && isFavorite((SoundWrapper) wrapper);
    }

    public boolean canFavorite() {
        return player.hasPermission("bending.command.vocalmenu.favorite");
    }

    public int getMaxFavorites() {
        return maxFavorites;
    }

    public List<SoundWrapper> getFavorites() {
        return new ArrayList<>(favorites);
    }

    public boolean addFavorite(SoundWrapper wrapper) {
        if (favorites.size() < getMaxFavorites()) {
            String test = favorites.stream().map(w -> w.path).collect(Collectors.joining(";"));
            if (test.length() > 512) return false; //Database limitation

            favorites.add(wrapper);
            saveFavorites();
            return true;
        }
        return false;
    }

    public void removeFavorite(SoundWrapper wrapper) {
        favorites.remove(wrapper);
        saveFavorites();
    }

    public void recalculateMax() {
        //Check the max amount of favorites from permissions
        String base = "bending.command.vocalmenu.favorite.";
        for (int i = HARDCODED_MAX_FAVORITES; i > 0; i--) {
            String perm = base + i;
            if (player.hasPermission(perm)) {
                this.maxFavorites = i;
                break;
            }
        }
    }

    public void saveFavorites() {
        DatabaseUtil.setFavorites(player, favorites.stream().map(w -> w.path).toArray(String[]::new));
    }

    public MenuBase getLastOpen() {
        return lastOpen;
    }

    public void setLastOpen(MenuBase lastOpen) {
        this.lastOpen = lastOpen;
    }

    public Sound getSetSound() {
        return setSound;
    }

    public void setSetSound(Sound setSound) {
        this.setSound = setSound;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
