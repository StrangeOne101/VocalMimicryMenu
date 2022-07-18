package com.strangeone101.vocalmimicrymenu.resources;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EmptyMenuItem extends MenuItem {

    public EmptyMenuItem(String path, Material stack, int amount) {
        super(path, stack, amount);
    }

    @Override
    public void onClick(Player player) {}
}
