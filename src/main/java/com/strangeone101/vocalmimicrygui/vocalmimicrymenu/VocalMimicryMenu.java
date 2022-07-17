package com.strangeone101.vocalmimicrygui.vocalmimicrymenu;

import com.strangeone101.vocalmimicrygui.vocalmimicrymenu.resources.Listener;
import me.simplicitee.project.addons.ability.air.VocalMimicry;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class VocalMimicryMenu extends JavaPlugin {

    public static VocalMimicryMenu INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        new VocalMenuCommand();
        new SoundBreakdown();

        Bukkit.getPluginManager().registerEvents(new Listener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void setVocalSound(Player player, Sound sound) {
        VocalMimicry.selectSound(player, sound);
    }
}