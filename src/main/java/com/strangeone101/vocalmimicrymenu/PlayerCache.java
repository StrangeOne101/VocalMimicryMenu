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

public class PlayerCache {

    private static Map<Player, PlayerCache> CACHE = new HashMap<>();

    private Player player;
    private MenuBase lastOpen;
    private Sound setSound;
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

    public List<SoundWrapper> getFavorites() {
        return favorites;
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
