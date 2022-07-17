package com.strangeone101.vocalmimicrymenu;

import com.projectkorra.projectkorra.command.PKCommand;
import com.strangeone101.vocalmimicrymenu.menu.SoundViewerMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class VocalMenuCommand extends PKCommand implements CommandExecutor {

    public VocalMenuCommand() {
        super("vocalmenu", "/bending vocalmenu", "Opens a menu to configure VocalMimicry", new String[] {"vocalmenu", "vmc", "vocamimi", "vcl"});

        VocalMimicryMenu.INSTANCE.getCommand("vocalmenu").setExecutor(this);
    }

    @Override
    public void execute(CommandSender commandSender, List<String> list) {
        trueExecute(commandSender, list);
    }

    public boolean trueExecute(CommandSender sender, List<String> args) {
        if (!hasPermission(sender)) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        SoundViewerMenu.openForPlayer((Player) sender);

        //System.out.println(SoundBreakdown.getInstance().BASE.toString());

        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return trueExecute(sender, Arrays.asList(args));
    }
}
