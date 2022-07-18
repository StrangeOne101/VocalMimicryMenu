package com.strangeone101.vocalmimicrymenu;

import com.strangeone101.vocalmimicrymenu.resources.MenuListener;
import me.simplicitee.project.addons.ProjectAddons;
import me.simplicitee.project.addons.ability.air.VocalMimicry;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class VocalMimicryMenu extends JavaPlugin {

    public static VocalMimicryMenu INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        new VocalMenuCommand();
        new SoundBreakdown();

        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new Listener(), this);

        Permission perm = Bukkit.getPluginManager().getPermission("bending.command.vocalmenu");
        if (perm == null) {
            perm = new Permission("bending.command.vocalmenu");
            Bukkit.getPluginManager().addPermission(perm);
        }
        Permission parent = Bukkit.getPluginManager().getPermission("bending.player");
        perm.addParent(parent, true);

        getLogger().info("VocalMimicryMenu has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean setVocalSound(Player player, Sound sound) {
        if (isBlacklisted(sound)) return false;

        VocalMimicry.selectSound(player, sound);
        return true;
    }

    public static boolean isBlacklisted(Sound sound) {
        List<String> blacklist = ProjectAddons.instance.config().get().getStringList("Abilities.Air.VocalMimicry.SoundBlacklist");
        for (String blackSound : blacklist) {
            if (sound.name().equalsIgnoreCase(blackSound)) {
                return true;
            }
        }
        return false;
    }
}
