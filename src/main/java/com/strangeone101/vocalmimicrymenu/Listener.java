package com.strangeone101.vocalmimicrymenu;

import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.command.PKCommand;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import com.strangeone101.vocalmimicrymenu.menu.SoundViewerMenu;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {

    private final String[] help = new String[] {"help", "h"};

    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {
        PlayerCache.release(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final String[] splits = event.getMessage().toLowerCase().split(" ");
        if (splits.length != 3) return;
        final Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(VocalMimicryMenu.INSTANCE, () -> {
            for (String s : Commands.commandaliases) {
                if (splits[0].equals("/" + s)) {
                    for (String s1 : help) {
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

    @EventHandler
    public void onBendingReload(BendingReloadEvent event) {
        SoundBreakdown.INSTANCE = null;
        new SoundBreakdown(); //Reload all sounds to newly blacklisted ones are known about
    }
}
