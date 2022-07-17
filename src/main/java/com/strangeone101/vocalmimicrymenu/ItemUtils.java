package com.strangeone101.vocalmimicrymenu;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

import java.util.Random;
import java.util.UUID;

public class ItemUtils {

    private static Method setProfile;

    /**
     * Sets the skin of a Skull to the skin provided. Can be a UUID, name, texture ID or URL
     * @param meta The skull meta
     * @param skin The skin
     */
    public static void setSkin(SkullMeta meta, String skin) {
        try {
            UUID uuid = UUID.fromString(skin);
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
            return;
        } catch (IllegalArgumentException ignored) { }

        if (skin.startsWith("https://") || skin.startsWith("http://")) {
            meta = setSkinFromURL(meta, skin);
        } else if (skin.matches("[\\w\\d_]{3,16}")) { //The username format
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(skin));
        } else if (skin.matches("[A-Fa-f\\d]{52,64}")) { //The skin format
            meta = setSkinFromURL(meta, "http://textures.minecraft.net/texture/" + skin);
        } else {
            VocalMimicryMenu.INSTANCE.getLogger().warning("Invalid skin format: " + skin);
        }
    }

    /**
     * Sets the skin of a Skull to the skin from the provided URL
     * @param meta The skull meta
     * @param skin The skin URL
     * @return The corrected ItemMeta
     */
    public static SkullMeta setSkinFromURL(SkullMeta meta, String skin) {
        Random random = new Random(skin.hashCode());

        //The UUID must be using a random seeded from the hashcode, so the item can stack
        GameProfile profile = new GameProfile(new UUID(random.nextLong(), random.nextLong()), null);
        byte[] encodedData = Base64.getEncoder()
                .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", skin).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        try {
            if (setProfile == null) {
                setProfile = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                setProfile.setAccessible(true);
            }

            setProfile.invoke(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return meta;
    }

    public static ItemStack getFromString(String itemstack) {
        String materialString = itemstack.split(":")[0].toUpperCase();
        String extra = itemstack.split(":").length > 1 ? itemstack.split(":")[1] : "";

        Material material = Material.PAPER;
        if (Material.getMaterial(materialString) != null) {
            material = Material.getMaterial(materialString);
        }
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();

        if (material == Material.POTION) {
            PotionMeta potionMeta = (PotionMeta) meta;
            if (extra.equals("")) {
                potionMeta.setBasePotionData(new PotionData(PotionType.WATER));
            } else {
                if (extra.matches("\\d{1,16}")) {
                    int color = Integer.parseInt(extra);
                    potionMeta.setColor(org.bukkit.Color.fromRGB(color));
                }
            }
            meta = potionMeta;
        } else if (material == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta) meta;
            if (!extra.equals("")) {
                setSkin(skullMeta, extra);
                meta = skullMeta;
            }
        }

        stack.setItemMeta(meta);
        return stack;
    }
}
