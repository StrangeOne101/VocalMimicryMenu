package com.strangeone101.vocalmimicrymenu.wrapper;

import com.strangeone101.vocalmimicrymenu.SoundBreakdown;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class SoundWrapper extends SoundWrapperBase {

    public Sound sound;

    public SoundWrapper(String path, Sound sound, ItemStack stack, String name) {
        this.path = path;
        this.stack = stack;
        this.name = name;
        this.sound = sound;
    }

    public void addToContainer(SoundsContainer container) {
        container.list.add(this);
        this.parent = container;
    }

    public void register() {
        SoundBreakdown.getInstance().SOUND_ENTRIES.put(this.path, this);
    }

    @Override
    public String toString() {
        return path;
    }
}
