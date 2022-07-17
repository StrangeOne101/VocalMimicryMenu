package com.strangeone101.vocalmimicrymenu.wrapper;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SoundsContainer extends SoundWrapperBase {

    public List<SoundWrapperBase> list;

    public SoundsContainer(String path, ItemStack stack, String name) {
        this.path = path;
        this.stack = stack;
        this.name = name;
        this.list = new ArrayList<>();
    }

    @Override
    public String toString() {
        return path + list.toString();
    }
}
