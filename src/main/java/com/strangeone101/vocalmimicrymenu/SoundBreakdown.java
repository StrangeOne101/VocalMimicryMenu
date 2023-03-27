package com.strangeone101.vocalmimicrymenu;

import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapper;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundWrapperBase;
import com.strangeone101.vocalmimicrymenu.wrapper.SoundsContainer;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundBreakdown {

    static SoundBreakdown INSTANCE;

    public SoundBreakdown() {
        if (INSTANCE == null) {
            INSTANCE = this;

            Bukkit.getScheduler().runTaskAsynchronously(VocalMimicryMenu.INSTANCE, this::fillSounds);
        }
    }

    private List<Material> BLOCKS = new ArrayList<>();
    private List<Material> ITEMS = new ArrayList<>();

    private Map<String, String> PASSIVE_MOB_TEXTURES = new LinkedHashMap<>();
    private Map<String, String> HOSTILE_MOB_TEXTURES = new LinkedHashMap<>();
    private Map<String, String> OTHER_MOB_TEXTURES = new LinkedHashMap<>();

    private Map<String, String> ALL_MOB_TEXTURES = new LinkedHashMap<>();
    private int PASSIVE_TEXTURES = 0;
    private int HOSTILE_TEXTURES = 0;
    private int OTHER_TEXTURES = 0;

    private final Map<String, String> ITEM_TEXTURES = new LinkedHashMap<>();
    private final Map<String, String> BLOCK_TEXTURES = new LinkedHashMap<>();

    public SoundsContainer BASE;
    public final Map<String, SoundWrapperBase> SOUND_ENTRIES = new LinkedHashMap<>();

    private boolean ready = false;

    private void fillNonAutoTextures() {
        PASSIVE_MOB_TEXTURES.put("pig", "PLAYER_HEAD:2504bc7c7d6a88cac41ceff457bf553a5c8bad7a221b62f92a4bb6db846df0f0");
        PASSIVE_MOB_TEXTURES.put("cow", "PLAYER_HEAD:387810fcf5f3a27dda4580bdc761867d14487fdecf0e138c5830d92be33b48a2");
        PASSIVE_MOB_TEXTURES.put("sheep", "PLAYER_HEAD:6c6f4fb21c742e0151d158616ef31128ff710a2f33e22ffe64d786bdc459bba6");
        PASSIVE_MOB_TEXTURES.put("chicken", "PLAYER_HEAD:ca571a587d379201ff5daf5967be59fcb86fcd6097da4ed7db1a23d09c875c9d");
        PASSIVE_MOB_TEXTURES.put("mooshroom", "PLAYER_HEAD:27cc34e09c048025c2347a4546549625d433bd599ba769c19eef93e84fac86b6");
        PASSIVE_MOB_TEXTURES.put("squid", "PLAYER_HEAD:fbeb2056d74bc9922fcace84e594ca33f28aceb5acd04f5aa6f547b13f10472");
        PASSIVE_MOB_TEXTURES.put("villager", "PLAYER_HEAD:742121346053846b506eac0e5f0d06b0bcf2beab4d7d96f6d5af1a4aefc3a887");
        PASSIVE_MOB_TEXTURES.put("player", "PLAYER_HEAD");
        PASSIVE_MOB_TEXTURES.put("rabbit", "PLAYER_HEAD:bfcf6d167c6817e7f3481c055eb07d57637903690c79e7ce0bc703cf1f0e7dd6");
        PASSIVE_MOB_TEXTURES.put("snow_golem", "PLAYER_HEAD:1fdfd1f7538c040258be7a91446da89ed845cc5ef728eb5e690543378fcf4");
        PASSIVE_MOB_TEXTURES.put("iron_golem", "PLAYER_HEAD:89091d79ea0f59ef7ef94d7bba6e5f17f2f7d4572c44f90f76c4819a714");
        PASSIVE_MOB_TEXTURES.put("horse", "PLAYER_HEAD:2990821951fd9ab9301aebf5d76a9b60f15ebf619e1c9e8d668919d394d9a933");
        PASSIVE_MOB_TEXTURES.put("donkey", "PLAYER_HEAD:2144bdad6bc18a3716b196dc4a4bd695265eccaadd0d945beb976443f82693b");
        PASSIVE_MOB_TEXTURES.put("mule", "PLAYER_HEAD:a0486a742e7dda0bae61ce2f55fa13527f1c3b334c57c034bb4cf132fb5f5f");
        PASSIVE_MOB_TEXTURES.put("bat", "PLAYER_HEAD:6681a72da7263ca9aef066542ecca7a180c40e328c0463fcb114cb3b83057552");
        PASSIVE_MOB_TEXTURES.put("ocelot", "PLAYER_HEAD:5657cd5c2989ff97570fec4ddcdc6926a68a3393250c1be1f0b114a1db1");
        PASSIVE_MOB_TEXTURES.put("cat", "PLAYER_HEAD:68c2f1f7e8cd6b00d30f0edaeefce38e889173c30c701fac0da860e0a2125ec8");
        PASSIVE_MOB_TEXTURES.put("wolf", "PLAYER_HEAD:dc3dd984bb659849bd52994046964c22725f717e986b12d548fd169367d494");
        PASSIVE_MOB_TEXTURES.put("turtle", "PLAYER_HEAD:0a4050e7aacc4539202658fdc339dd182d7e322f9fbcc4d5f99b5718a");
        PASSIVE_MOB_TEXTURES.put("dolphin", "PLAYER_HEAD:8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3");
        PASSIVE_MOB_TEXTURES.put("parrot", "PLAYER_HEAD:9a66a062337f68409a8d350c7b55f4b44bbf125dfecec6a5820cbb4edefb4e5c");
        PASSIVE_MOB_TEXTURES.put("cod", "COD");
        PASSIVE_MOB_TEXTURES.put("salmon", "SALMON");
        PASSIVE_MOB_TEXTURES.put("puffer_fish", "PUFFERFISH");
        PASSIVE_MOB_TEXTURES.put("tropical_fish", "TROPICAL_FISH");
        PASSIVE_MOB_TEXTURES.put("llama", "PLAYER_HEAD:2a5f10e6e6232f182fe966f501f1c3799d45ae19031a1e4941b5dee0feff059b");
        PASSIVE_MOB_TEXTURES.put("polar_bear", "PLAYER_HEAD:c4fe926922fbb406f343b34a10bb98992cee4410137d3f88099427b22de3ab90");
        PASSIVE_MOB_TEXTURES.put("wandering_trader", "PLAYER_HEAD:5f1379a82290d7abe1efaabbc70710ff2ec02dd34ade386bc00c930c461cf932");
        PASSIVE_MOB_TEXTURES.put("fox", "PLAYER_HEAD:a20725c15a7b0109d425ab091869616aaa651ee43383863906b4410d18ea86de");
        PASSIVE_MOB_TEXTURES.put("bee", "PLAYER_HEAD:f0ad6de4e1d55851f570346e392745c57d9a0cbaa6d49fb6a0cadac1319c18fa");
        PASSIVE_MOB_TEXTURES.put("panda", "PLAYER_HEAD:dca096eea506301bea6d4b17ee1605625a6f5082c71f74a639cc940439f47166");
        PASSIVE_MOB_TEXTURES.put("strider", "PLAYER_HEAD:cb7ffdda656c68d88851a8e05b48cd2493773ffc4ab7d64e9302229fe3571059");
        PASSIVE_MOB_TEXTURES.put("glow_squid", "PLAYER_HEAD:3e94a1bb1cb00aaa153a74daf4b0eea20b8974522fe9901eb55aef478ebeff0d");
        PASSIVE_MOB_TEXTURES.put("goat", "PLAYER_HEAD:f03330398a0d833f53ae8c9a1cb393c74e9d31e18885870e86a2133d44f0c63c");
        PASSIVE_MOB_TEXTURES.put("axolotl", "PLAYER_HEAD:766dd0c3b4ec07f789c092f39f34fb68061666b6ef61f7e001e3e6f166996e6e");
        PASSIVE_MOB_TEXTURES.put("frog", "PLAYER_HEAD:ce62e8a048d040eb0533ba26a866cd9c2d0928c931c50b4482ac3a3261fab6f0");
        PASSIVE_MOB_TEXTURES.put("tadpole", "PLAYER_HEAD:b23ebf26b7a441e10a86fb5c2a5f3b519258a5c5dddd6a1a75549f517332815b");
        PASSIVE_MOB_TEXTURES.put("allay", "PLAYER_HEAD:beea845cc0b58ff763decffe11cd1c845c5d09c3b04fe80b0663da5c7c699eb3");
        PASSIVE_MOB_TEXTURES.put("camel", "PLAYER_HEAD:74b8a333dfa92e7e5a95ad4ae2d84b1bafa33dc28c054925277f60e79dafc8c4");
        PASSIVE_MOB_TEXTURES.put("sniffer", "PLAYER_HEAD:43f3be09a7353eeae94d88320cb5b242de2f719c0e5c16a486327c605db1d463");

        HOSTILE_TEXTURES = ALL_MOB_TEXTURES.size();

        HOSTILE_MOB_TEXTURES.put("zombie", "ZOMBIE_HEAD");
        HOSTILE_MOB_TEXTURES.put("skeleton", "SKELETON_SKULL");
        HOSTILE_MOB_TEXTURES.put("spider", "PLAYER_HEAD:cd541541daaff50896cd258bdbdd4cf80c3ba816735726078bfe393927e57f1");
        HOSTILE_MOB_TEXTURES.put("creeper", "CREEPER_HEAD");
        HOSTILE_MOB_TEXTURES.put("enderman", "PLAYER_HEAD:7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf");
        HOSTILE_MOB_TEXTURES.put("slime", "PLAYER_HEAD:a20e84d32d1e9c919d3fdbb53f2b37ba274c121c57b2810e5a472f40dacf004f");
        HOSTILE_MOB_TEXTURES.put("cave_spider", "PLAYER_HEAD:41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224");
        HOSTILE_MOB_TEXTURES.put("ghast", "PLAYER_HEAD:8b6a72138d69fbbd2fea3fa251cabd87152e4f1c97e5f986bf685571db3cc0");
        HOSTILE_MOB_TEXTURES.put("blaze", "PLAYER_HEAD:b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0");
        HOSTILE_MOB_TEXTURES.put("magma_cube", "PLAYER_HEAD:38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429");
        HOSTILE_MOB_TEXTURES.put("zombie_pigman", "PLAYER_HEAD:74e9c6e98582ffd8ff8feb3322cd1849c43fb16b158abb11ca7b42eda7743eb"); //Pre 1.16
        HOSTILE_MOB_TEXTURES.put("zombified_piglin", "PLAYER_HEAD:e935842af769380f78e8b8a88d1ea6ca2807c1e5693c2cf797456620833e936f"); //1.16+
        HOSTILE_MOB_TEXTURES.put("ender_dragon", "PLAYER_HEAD:74ecc040785e54663e855ef0486da72154d69bb4b7424b7381ccf95b095a");
        HOSTILE_MOB_TEXTURES.put("silverfish", "PLAYER_HEAD:da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540");
        HOSTILE_MOB_TEXTURES.put("zombie_villager", "PLAYER_HEAD:8c7505f224d5164a117d8c69f015f99eff434471c8a2df907096c4242c3524e8");
        HOSTILE_MOB_TEXTURES.put("witch", "PLAYER_HEAD:20e13d18474fc94ed55aeb7069566e4687d773dac16f4c3f8722fc95bf9f2dfa");
        HOSTILE_MOB_TEXTURES.put("wither_skeleton", "WITHER_SKELETON_SKULL");
        HOSTILE_MOB_TEXTURES.put("wither", "PLAYER_HEAD:5da97c06c07a035bd8746b48d32dc04e84160bd161f26be1272b6271251aaa7");
        HOSTILE_MOB_TEXTURES.put("zombie_horse", "PLAYER_HEAD:d22950f2d3efddb18de86f8f55ac518dce73f12a6e0f8636d551d8eb480ceec");
        HOSTILE_MOB_TEXTURES.put("skeleton_horse", "PLAYER_HEAD:47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a");
        HOSTILE_MOB_TEXTURES.put("guardian", "PLAYER_HEAD:a0bf34a71e7715b6ba52d5dd1bae5cb85f773dc9b0d457b4bfc5f9dd3cc7c94");
        HOSTILE_MOB_TEXTURES.put("elder_guardian", "PLAYER_HEAD:4340a268f25fd5cc276ca147a8446b2630a55867a2349f7ca107c26eb58991");
        HOSTILE_MOB_TEXTURES.put("endermite", "PLAYER_HEAD:ff4694ec8cbce371932504366aa3d6818b5364d8baff20b6d3be2f9093a5213d");
        HOSTILE_MOB_TEXTURES.put("shulker", "PLAYER_HEAD:1433a4b73273a64c8ab2830b0fff777a61a488c92f60f83bfb3e421f428a44");
        HOSTILE_MOB_TEXTURES.put("husk", "PLAYER_HEAD:d674c63c8db5f4ca628d69a3b1f8a36e29d8fd775e1a6bdb6cabb4be4db121");
        HOSTILE_MOB_TEXTURES.put("stray", "PLAYER_HEAD:78ddf76e555dd5c4aa8a0a5fc584520cd63d489c253de969f7f22f85a9a2d56");
        HOSTILE_MOB_TEXTURES.put("vindicator", "PLAYER_HEAD:6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173");
        HOSTILE_MOB_TEXTURES.put("evoker", "PLAYER_HEAD:6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173");
        HOSTILE_MOB_TEXTURES.put("illusioner", "PLAYER_HEAD:512512e7d016a2343a7bff1a4cd15357ab851579f1389bd4e3a24cbeb88b");
        HOSTILE_MOB_TEXTURES.put("vex", "PLAYER_HEAD:c2ec5a516617ff1573cd2f9d5f3969f56d5575c4ff4efefabd2a18dc7ab98cd");
        HOSTILE_MOB_TEXTURES.put("drowned", "PLAYER_HEAD:c84df79c49104b198cdad6d99fd0d0bcf1531c92d4ab6269e40b7d3cbbb8e98c");
        HOSTILE_MOB_TEXTURES.put("phantom", "PLAYER_HEAD:746830da5f83a3aaed838a99156ad781a789cfcf13e25beef7f54a86e4fa4");
        HOSTILE_MOB_TEXTURES.put("pillager", "PLAYER_HEAD:6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173");
        HOSTILE_MOB_TEXTURES.put("ravager", "PLAYER_HEAD:cd20bf52ec390a0799299184fc678bf84cf732bb1bd78fd1c4b441858f0235a8");
        HOSTILE_MOB_TEXTURES.put("piglin", "PLAYER_HEAD:d71b3aee182b9a99ed26cbf5ecb47ae90c2c3adc0927dde102c7b30fdf7f4545");
        HOSTILE_MOB_TEXTURES.put("piglin_brute", "PLAYER_HEAD:3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf");
        HOSTILE_MOB_TEXTURES.put("hoglin", "PLAYER_HEAD:9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75");
        HOSTILE_MOB_TEXTURES.put("zoglin", "PLAYER_HEAD:e67e18602e03035ad68967ce090235d8996663fb9ea47578d3a7ebbc42a5ccf9");
        HOSTILE_MOB_TEXTURES.put("warden", "PLAYER_HEAD:c6f74361fb00490a0a98eeb814544ecdd775cb55633dbb114e60d27004cb1020");

        OTHER_TEXTURES = ALL_MOB_TEXTURES.size();

        OTHER_MOB_TEXTURES.put("armor_stand", "ARMOR_STAND");
        OTHER_MOB_TEXTURES.put("boat", Material.getMaterial("BOAT") != null ? "BOAT" : "OAK_BOAT");
        OTHER_MOB_TEXTURES.put("arrow", "ARROW");
        OTHER_MOB_TEXTURES.put("snowball", "SNOWBALL");
        OTHER_MOB_TEXTURES.put("egg", "EGG");
        OTHER_MOB_TEXTURES.put("experience_bottle", "EXPERIENCE_BOTTLE");
        OTHER_MOB_TEXTURES.put("generic", "PLAYER_HEAD");
        OTHER_MOB_TEXTURES.put("item", "IRON_SHOVEL");
        OTHER_MOB_TEXTURES.put("item_frame", "ITEM_FRAME");
        OTHER_MOB_TEXTURES.put("glow_item_frame", "GLOW_ITEM_FRAME");
        OTHER_MOB_TEXTURES.put("hostile", "ZOMBIE_HEAD");
        OTHER_MOB_TEXTURES.put("leash_knot", "LEAD");
        OTHER_MOB_TEXTURES.put("painting", "PAINTING");
        OTHER_MOB_TEXTURES.put("splash_potion", "BREWING_STAND");
        OTHER_MOB_TEXTURES.put("minecart", "MINECART");
        OTHER_MOB_TEXTURES.put("lightning_bolt", "END_ROD");
        OTHER_MOB_TEXTURES.put("lingering_potion", "DRAGON_BREATH");
        OTHER_MOB_TEXTURES.put("firework_rocket", "FIREWORK_ROCKET");
        OTHER_MOB_TEXTURES.put("fishing_bobber", "FISHING_ROD");

        ALL_MOB_TEXTURES.putAll(PASSIVE_MOB_TEXTURES);
        ALL_MOB_TEXTURES.putAll(HOSTILE_MOB_TEXTURES);
        ALL_MOB_TEXTURES.putAll(OTHER_MOB_TEXTURES);

        ITEM_TEXTURES.put("AXE", "IRON_AXE");
        ITEM_TEXTURES.put("BOTTLE", "GLASS_BOTTLE");
        ITEM_TEXTURES.put("DYE", "RED_DYE");
        ITEM_TEXTURES.put("FLINTANDSTEEL", "FLINT_AND_STEEL");
        ITEM_TEXTURES.put("FIRECHARGE", "FIRE_CHARGE");
        ITEM_TEXTURES.put("HOE", "IRON_HOE");
        ITEM_TEXTURES.put("TOTEM", "TOTEM_OF_UNDYING");
        ITEM_TEXTURES.put("SHOVEL", "IRON_SHOVEL");
        ITEM_TEXTURES.put("ARMOR_EQUIP", "IRON_CHESTPLATE");

        BLOCK_TEXTURES.put("WOOL", "WHITE_WOOL");
        BLOCK_TEXTURES.put("FIRE", "FLINT_AND_STEEL");
        BLOCK_TEXTURES.put("LAVA", "LAVA_BUCKET");
        BLOCK_TEXTURES.put("WATER", "WATER_BUCKET");
        BLOCK_TEXTURES.put("METAL", "IRON_BLOCK");
        BLOCK_TEXTURES.put("WOODEN_DOOR", "OAK_DOOR");
        BLOCK_TEXTURES.put("WOODEN_TRAPDOOR", "OAK_TRAPDOOR");
        BLOCK_TEXTURES.put("WET_GRASS", "KELP");
        BLOCK_TEXTURES.put("TRIPWIRE", "TRIPWIRE_HOOK");
        BLOCK_TEXTURES.put("STEM", "PUMPKIN");
        BLOCK_TEXTURES.put("POWDER_SNOW", "POWDER_SNOW_BUCKET");
        BLOCK_TEXTURES.put("PORTAL", "OBSIDIAN");
        BLOCK_TEXTURES.put("GRASS", "GRASS_BLOCK");
        BLOCK_TEXTURES.put("FUNGUS", "WARPED_FUNGUS");
        BLOCK_TEXTURES.put("FROGSPAWN", "FROG_SPAWN_EGG");
        BLOCK_TEXTURES.put("CROP", "WHEAT");
        BLOCK_TEXTURES.put("BAMBOO_SAPLING", "BAMBOO");
        BLOCK_TEXTURES.put("CAVE_VINES", "GLOW_BERRIES");
        BLOCK_TEXTURES.put("END_GATEWAY", "BEDROCK");
        BLOCK_TEXTURES.put("END_PORTAL", "OBSIDIAN");
        BLOCK_TEXTURES.put("COPPER", "COPPER_BLOCK");
        BLOCK_TEXTURES.put("BUBBLE_COLUMN", "MAGMA_BLOCK");
        BLOCK_TEXTURES.put("METAL_PRESSURE_PLATE", "HEAVY_WEIGHTED_PRESSURE_PLATE");
        BLOCK_TEXTURES.put("ROOTS", "WARPED_ROOTS");
        BLOCK_TEXTURES.put("SWEET_BERRY_BUSH", "SWEET_BERRIES");

    }

    private void fillAutoTextures() {
        List<String> allMobKeys = new ArrayList<>(ALL_MOB_TEXTURES.keySet());
        Collections.sort(allMobKeys, Comparator.comparingInt(String::length).reversed()); //Make the longer keys be first

        BASE = new SoundsContainer("", new ItemStack(Material.BEDROCK), "selecting a category");

        for (Category category : Category.values()) {
            SoundsContainer container = new SoundsContainer(category.getName().toLowerCase(), new ItemStack(category.getMaterial()), category.getName() + " Category");
            container.parent = BASE;
            SOUND_ENTRIES.put(category.getName().toLowerCase(), container);
            BASE.list.add(container);
        }

        SoundsContainer passive = new SoundsContainer("mobs.passive", ItemUtils.getFromString(ALL_MOB_TEXTURES.get("pig")), "Passive Mobs");
        SoundsContainer hostile = new SoundsContainer("mobs.hostile", new ItemStack(Material.ZOMBIE_HEAD), "Hostile Mobs");
        SoundsContainer other = new SoundsContainer("mobs.other", new ItemStack(Material.EXPERIENCE_BOTTLE), "Other Entities");
        passive.parent = (SoundsContainer) SOUND_ENTRIES.get("mobs");
        hostile.parent = (SoundsContainer) SOUND_ENTRIES.get("mobs");
        other.parent = (SoundsContainer) SOUND_ENTRIES.get("mobs");
        SOUND_ENTRIES.put("mobs.passive", passive);
        SOUND_ENTRIES.put("mobs.hostile", hostile);
        SOUND_ENTRIES.put("mobs.other", other);
        ((SoundsContainer)SOUND_ENTRIES.get("mobs")).list.addAll(Arrays.asList(passive, hostile, other));

        soundloop:
        for (Sound sound : Sound.values()) {
            if (VocalMimicryMenu.isBlacklisted(sound)) continue;

            String asString = sound.name().toLowerCase();
            if (asString.startsWith("entity")) {
                asString = asString.substring("entity_".length());

                String[] split = asString.split("_");
                for (int i = split.length - 1; i > 0; i--) {

                    String attempedMobName = Strings.join(Arrays.asList(Arrays.copyOfRange(split, 0, i)), '_');
                    if (allMobKeys.contains(attempedMobName)) {
                        String mobType = PASSIVE_MOB_TEXTURES.containsKey(attempedMobName) ? "passive" :
                                HOSTILE_MOB_TEXTURES.containsKey(attempedMobName) ? "hostile" :
                                        OTHER_MOB_TEXTURES.containsKey(attempedMobName) ? "other" : "bugged";

                        String soundType = asString.substring(attempedMobName.length() + 1);
                        ItemStack stack = getSoundItemStack(Category.MOBS, attempedMobName, soundType);
                        String name = sound.name().substring("entity_".length());
                        SoundWrapper wrapper = new SoundWrapper("mobs." + mobType + "." + attempedMobName + "." + soundType.toLowerCase(), sound, stack, getFancyName(name));
                        wrapper.addToContainer(getEntityContainer(attempedMobName));
                        wrapper.register();
                        continue soundloop;
                    } else {
                        String soundType = asString.substring(attempedMobName.length() + 1);
                        ItemStack stack = getSoundItemStack(Category.MOBS, attempedMobName, soundType);
                        String name = sound.name().substring("entity_".length());
                        SoundWrapper wrapper = new SoundWrapper("mobs.other." + attempedMobName + "." + soundType.toLowerCase(), sound, stack, getFancyName(name));
                        wrapper.addToContainer(getEntityContainer(attempedMobName));
                        wrapper.register();
                        continue soundloop;
                    }

                }
            } else if (asString.startsWith("block")) {
                asString = asString.substring("block_".length());

                for (Material block : BLOCKS) {
                    if (asString.startsWith(block.name().toLowerCase())) {
                        String soundType = asString.substring(block.name().length() + 1);
                        ItemStack stack = getSoundItemStack(Category.BLOCKS, block.name(), soundType);
                        String name = sound.name().substring("block_".length());
                        SoundWrapper wrapper = new SoundWrapper("blocks." + block.name().toLowerCase() + "." + soundType.toLowerCase(), sound, stack, getFancyName(name));
                        wrapper.addToContainer(getBlockContainer(block.name()));
                        wrapper.register();
                        continue soundloop;
                    }
                }

                for (String blockKey : BLOCK_TEXTURES.keySet()) {
                    if (asString.startsWith(blockKey.toLowerCase())) {
                        String soundType = asString.substring(blockKey.length() + 1);
                        ItemStack stack = getSoundItemStack(Category.BLOCKS, blockKey, soundType);
                        String name = sound.name().substring("block_".length());
                        SoundWrapper wrapper = new SoundWrapper("blocks." + blockKey.toLowerCase() + "." + soundType.toLowerCase(), sound, stack, getFancyName(name));
                        wrapper.addToContainer(getBlockContainer(blockKey));
                        wrapper.register();
                        continue soundloop;
                    }
                }
            } else if (asString.startsWith("item")) {
                asString = asString.substring("item_".length());

                for (Material item : ITEMS) {
                    if (asString.startsWith(item.name().toLowerCase())) {
                        String soundType = asString.substring(item.name().length() + 1);
                        ItemStack stack = getSoundItemStack(Category.ITEMS, item.name(), soundType);
                        String name = sound.name().substring("item_".length());
                        SoundWrapper wrapper = new SoundWrapper("items." + item.name().toLowerCase() + "." + soundType.toLowerCase(), sound, stack, getFancyName(name));
                        wrapper.addToContainer(getItemContainer(item.name()));
                        wrapper.register();
                        continue soundloop;
                    }
                }

                for (String itemKey : ITEM_TEXTURES.keySet()) {
                    if (asString.startsWith(itemKey.toLowerCase())) {
                        String soundType = asString.substring(itemKey.length() + 1);
                        ItemStack stack = getSoundItemStack(Category.ITEMS, itemKey, soundType);
                        String name = sound.name().substring("item_".length());
                        SoundWrapper wrapper = new SoundWrapper("items." + itemKey.toLowerCase() + "." + soundType.toLowerCase(), sound, stack, getFancyName(name));
                        wrapper.addToContainer(getItemContainer(itemKey));
                        wrapper.register();
                        continue soundloop;
                    }
                }
            } else if (asString.startsWith("music")) {
                asString = asString.substring("music_".length());

                Material material = Material.PAPER;

                if (asString.startsWith("disc")) {
                    material = Material.getMaterial(sound.name());
                } else if (asString.equals("end")) material = Material.END_STONE;
                else if (asString.equals("under_water")) material = Material.WATER_BUCKET;
                else if (asString.equals("nether_basalt_deltas")) material = Material.getMaterial("BASALT");
                else if (asString.equals("nether_crimson_forest")) material = Material.getMaterial("NETHER_WART_BLOCK");
                else if (asString.equals("nether_nether_wastes")) material = Material.NETHERRACK;
                else if (asString.equals("nether_soul_sand_valley")) material = Material.SOUL_SAND;
                else if (asString.equals("nether_warped_forest")) material = Material.getMaterial("WARPED_WART_BLOCK");
                else if (asString.equals("overworld_deep_dark")) material = Material.getMaterial("DEEPSLATE_TILES");
                else if (asString.equals("overworld_dripstone_caves")) material = Material.getMaterial("DRIPSTONE_BLOCK");
                else if (asString.equals("overworld_frozen_peaks")) material = Material.PACKED_ICE;
                else if (asString.equals("overworld_grove")) material = Material.GRASS_BLOCK;
                else if (asString.equals("overworld_jagged_peaks")) material = Material.ANDESITE;
                else if (asString.equals("overworld_jungle_and_forest")) material = Material.JUNGLE_LEAVES;
                else if (asString.equals("overworld_lush_caves")) material = Material.getMaterial("MOSS_BLOCK");
                else if (asString.equals("overworld_meadow")) material = Material.GRASS_BLOCK;
                else if (asString.equals("overworld_old_growth_taiga")) material = Material.PODZOL;
                else if (asString.equals("overworld_snowy_slopes")) material = Material.SNOW_BLOCK;
                else if (asString.equals("overworld_stony_peaks")) material = Material.STONE;
                else if (asString.equals("overworld_swamp")) material = Material.LILY_PAD;
                else if (asString.equals("creative")) material = Material.YELLOW_TERRACOTTA;
                else if (asString.equals("dragon")) material = Material.OBSIDIAN;
                else if (asString.equals("menu")) material = Material.OAK_SIGN;
                else if (asString.equals("credits")) material = Material.ENDER_EYE;
                else if (asString.equals("game")) material = Material.DIAMOND;

                SoundWrapper wrapper = new SoundWrapper("music." + asString, sound, new ItemStack(material), getFancyName(asString));
                SoundsContainer container = (SoundsContainer) SOUND_ENTRIES.get("music");
                wrapper.addToContainer(container);
                wrapper.register();
            } else {
                SoundWrapper wrapper = new SoundWrapper("other." + asString, sound, new ItemStack(Material.PAPER), getFancyName(asString));
                SoundsContainer container = (SoundsContainer) SOUND_ENTRIES.get("other");
                wrapper.addToContainer(container);
                wrapper.register();
            }
        }
    }

    private SoundsContainer getBlockContainer(String blockName) {
        SoundWrapperBase container = SOUND_ENTRIES.get("blocks." + blockName.toLowerCase());
        if (container == null) {
            String materialName = blockName.toUpperCase();
            if (BLOCK_TEXTURES.containsKey(materialName)) materialName = BLOCK_TEXTURES.get(materialName);

            Material mat = Material.getMaterial(materialName);
            if (mat == null || !mat.isItem()) { //If it isn't an item, it can't be viewed in the inventory
                mat = Material.BEDROCK;
            }
            container = new SoundsContainer("blocks." + blockName.toLowerCase(), new ItemStack(mat), getFancyName(blockName));
            SOUND_ENTRIES.put("blocks." + blockName.toLowerCase(), container);
            SoundsContainer parent = ((SoundsContainer)SOUND_ENTRIES.get("blocks"));
            parent.list.add(container);
            container.parent = parent;
            return (SoundsContainer) container;
        }

        return (SoundsContainer) container;
    }

    private SoundsContainer getItemContainer(String itemName) {
        SoundWrapperBase container = SOUND_ENTRIES.get("items." + itemName.toLowerCase());
        if (container == null) {
            String materialName = itemName.toUpperCase();
            if (ITEM_TEXTURES.containsKey(materialName)) materialName = ITEM_TEXTURES.get(materialName);

            Material mat = Material.getMaterial(materialName);
            if (mat == null) mat = Material.BEDROCK;
            container = new SoundsContainer("items." + itemName.toLowerCase(), new ItemStack(mat), getFancyName(itemName));
            SOUND_ENTRIES.put("items." + itemName.toLowerCase(), container);
            SoundsContainer parent = ((SoundsContainer)SOUND_ENTRIES.get("items"));
            parent.list.add(container);
            container.parent = parent;
            return (SoundsContainer) container;
        }

        return (SoundsContainer) container;
    }

    private SoundsContainer getEntityContainer(String entityName) {
        String mobType = PASSIVE_MOB_TEXTURES.containsKey(entityName.toLowerCase()) ? "passive" :
                HOSTILE_MOB_TEXTURES.containsKey(entityName.toLowerCase()) ? "hostile" : "other";

        SoundWrapperBase container = SOUND_ENTRIES.get("mobs." + mobType + "." + entityName.toLowerCase());
        if (container == null) {
            String stackString = ALL_MOB_TEXTURES.get(entityName.toLowerCase());

            if (stackString == null) {
                Material spawnEgg = Material.getMaterial(mobType.toUpperCase() + "_SPAWN_EGG");
                if (spawnEgg != null) stackString = spawnEgg.name();
                else stackString = "EGG";
            }
            ItemStack stack = ItemUtils.getFromString(stackString);

            container = new SoundsContainer("mobs." + mobType + "." + entityName.toLowerCase(), stack, getFancyName(entityName));
            SOUND_ENTRIES.put("mobs." + mobType + "." + entityName.toLowerCase(), container);
            SoundsContainer parent = ((SoundsContainer)SOUND_ENTRIES.get("mobs." + mobType));
            parent.list.add(container);
            container.parent = parent;
            return (SoundsContainer) container;
        }

        return (SoundsContainer) container;
    }

    public ItemStack getSoundItemStack(Category category, String type, String soundExtension) {
        if (type.equalsIgnoreCase("parrot") && soundExtension.toLowerCase().startsWith("imitate")) {
            String imitateEntity = soundExtension.substring("imitate_".length());
            if (ALL_MOB_TEXTURES.containsKey(imitateEntity)) {
                return ItemUtils.getFromString(ALL_MOB_TEXTURES.get(imitateEntity));
            }
        } else if (type.equalsIgnoreCase("note_block") && category == Category.BLOCKS) {
            switch (soundExtension.toUpperCase()) {
                case "BASS": return new ItemStack(Material.OAK_PLANKS);
                case "SNARE": return new ItemStack(Material.SAND);
                case "HAT": return new ItemStack(Material.GLASS);
                case "BASEDRUM": return new ItemStack(Material.STONE);
                case "BELL": return new ItemStack(Material.GOLD_BLOCK);
                case "FLUTE": return new ItemStack(Material.CLAY);
                case "CHIME": return new ItemStack(Material.PACKED_ICE);
                case "GUITAR": return new ItemStack(Material.RED_WOOL);
                case "XYLOPHONE": return new ItemStack(Material.BONE_BLOCK);
                case "IRON_XYLOPHONE": return new ItemStack(Material.IRON_BLOCK);
                case "COW_BELL": return new ItemStack(Material.SOUL_SAND);
                case "DIDGERIDOO": return new ItemStack(Material.PUMPKIN);
                case "BIT": return new ItemStack(Material.EMERALD_BLOCK);
                case "BANJO": return new ItemStack(Material.HAY_BLOCK);
                case "PLING": return new ItemStack(Material.GLOWSTONE);
                case "HARP": return new ItemStack(Material.GRASS_BLOCK);
            }
        } else if (category == Category.BLOCKS) {
            switch (type.toUpperCase()) { //Cases where they only have a few sounds, so use the block as the icon
                case "END_PORTAL":
                case "END_PORTAL_FRAME":
                case "END_GATEWAY":
                case "CAMPFIRE":
                case "FURNACE":
                case "GRINDSTONE":
                case "SMITHING_TABLE":
                case "PISTON": {
                    if (BLOCK_TEXTURES.containsKey(type.toUpperCase())) return ItemUtils.getFromString(BLOCK_TEXTURES.get(type.toUpperCase()));
                    return ItemUtils.getFromString(type.toUpperCase());
                }
            }
            if (soundExtension.equalsIgnoreCase("use")) {
                if (BLOCK_TEXTURES.containsKey(type.toUpperCase()))
                    return ItemUtils.getFromString(BLOCK_TEXTURES.get(type.toUpperCase()));
                return ItemUtils.getFromString(type.toUpperCase());
            }
        } else if (category == Category.ITEMS) {
            if (soundExtension.equalsIgnoreCase("use")) {
                if (ITEM_TEXTURES.containsKey(type.toUpperCase())) return ItemUtils.getFromString(ITEM_TEXTURES.get(type.toUpperCase()));
                return ItemUtils.getFromString(type.toUpperCase());
            } else if (type.equalsIgnoreCase("armor_equip")) {
                switch (soundExtension.toUpperCase()) {
                    case "LEATHER": return new ItemStack(Material.LEATHER_CHESTPLATE);
                    case "CHAIN": return new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                    case "GENERIC":
                    case "IRON": return new ItemStack(Material.IRON_CHESTPLATE);
                    case "DIAMOND": return new ItemStack(Material.DIAMOND_CHESTPLATE);
                    case "GOLD": return new ItemStack(Material.GOLDEN_CHESTPLATE);
                    case "ELYTRA": return new ItemStack(Material.ELYTRA);
                    case "NETHERITE": return new ItemStack(Material.getMaterial("NETHERITE_CHESTPLATE"));
                    case "TURTLE": return new ItemStack(Material.TURTLE_HELMET);
                }
            } else if (type.equalsIgnoreCase("bucket")) {
                if (soundExtension.toLowerCase().startsWith("empty")) return new ItemStack(Material.BUCKET);
                switch (soundExtension.toUpperCase()) {
                    case "FILL":
                    case "FILL_WATER": return new ItemStack(Material.WATER_BUCKET);
                    case "FILL_LAVA": return new ItemStack(Material.LAVA_BUCKET);
                    case "FILL_POWDER_SNOW": return new ItemStack(Material.getMaterial("POWDER_SNOW_BUCKET"));
                    case "FILL_FISH": return new ItemStack(Material.getMaterial("SALMON_BUCKET"));
                    case "FILL_AXOLOTL": return new ItemStack(Material.getMaterial("AXOLOTL_BUCKET"));
                }
            }

        }

        Material mat = Material.PAPER;
        if (category == Category.BLOCKS) {
            switch(soundExtension.toUpperCase()) {
                case "BREAK": mat = Material.COBBLESTONE; break;
                case "PLACE": mat = Material.BLUE_TERRACOTTA; break;
                case "HIT": mat = Material.OAK_LOG; break;
                case "LAND":
                case "STEP": mat = Material.GRASS_BLOCK; break;
                case "FALL": mat = Material.HAY_BLOCK; break;
                case "AMBIENT": mat = Material.RED_STAINED_GLASS; break;
                case "AMBIENT_SHORT": mat = Material.PINK_STAINED_GLASS; break;
                case "EXTINGUISH": mat = Material.GRAY_CONCRETE; break;
                case "PICK_BERRIES": mat = Material.getMaterial("GLOW_BERRIES"); break;
                case "SHEAR":
                case "CARVE":
                case "CONVERT": mat = Material.SHEARS; break;
                case "CLICK_ON":
                case "DOOR_OPEN":
                case "OPEN":
                case "ACTIVATE":
                case "TRAPDOOR_OPEN": mat = Material.LIME_TERRACOTTA; break;
                case "CLICK_OFF":
                case "DOOR_CLOSE":
                case "CLOSE":
                case "DEACTIVATE":
                case "TRAPDOOR_CLOSE": mat = Material.RED_TERRACOTTA; break;
                case "ATTACH": mat = Material.TRIPWIRE_HOOK; break;
                case "DESTROY":
                case "DETACH": mat = Material.IRON_PICKAXE; break;
                case "CHARGE": mat = Material.FIRE_CHARGE; break;
                case "POP":
                case "LAVA":
                case "DRIP_LAVA": mat = Material.LAVA_BUCKET; break;
                case "CLICK": mat = Material.LEVER; break;
                case "BURNOUT": mat = Material.BLACK_CONCRETE; break;
                case "DRIP_WATER":
                case "WATER": mat = Material.WATER_BUCKET; break;
                case "DRIP_LAVA_INTO_CAULDRON":
                case "DRIP_WATER_INTO_CAULDRON": mat = Material.CAULDRON; break;
                case "SLIDE": mat = Material.HONEY_BLOCK; break;
                case "LOCKED": mat = Material.IRON_INGOT; break;
                case "CHIME": mat = Material.GOLD_BLOCK; break;
                case "RESONATE": mat = Material.BELL; break;
                case "ADD_CANDLE": mat = Material.getMaterial("CANDLE"); break;
                case "POWER_SELECT": mat = Material.EMERALD; break;
                case "ATTACK":
                case "ATTACK_SHORT": mat = Material.IRON_SWORD; break;
            }
            if (mat == Material.PAPER) {
                if (BLOCK_TEXTURES.containsKey(type.toUpperCase())) return ItemUtils.getFromString(BLOCK_TEXTURES.get(type));
                return ItemUtils.getFromString(type.toUpperCase());
            }
        } else if (category == Category.MOBS) {
            switch(soundExtension.toUpperCase()) {
                case "FLAP":
                case "AMBIENT_BABY":
                case "AMBIENT": mat = Material.FEATHER; break;
                case "SQUIRT":
                case "AMBIENT_WATER":
                case "CURSE":
                    mat = Material.INK_SAC; break;
                case "ATTACK":
                case "ATTACK_SWEEP":
                    mat = Material.IRON_SWORD; break;
                case "ANGRY": mat = Material.GOLD_NUGGET; break;
                case "HIT":
                case "HURT_LAND":
                case "HURT_BABY":
                case "HURT": mat = Material.REDSTONE; break;
                case "DEATH_SMALL":
                case "DEATH_BABY":
                case "DEATH_LAND":
                case "DEATH": mat = Material.WITHER_SKELETON_SKULL; break;
                case "EAT": mat = Material.CARROT; break;
                case "STEP_LAVA": mat = Material.LAVA_BUCKET; break;
                case "STEP": mat = Material.GOLDEN_BOOTS; break;
                case "BIG_FALL": mat = Material.DIAMOND_BOOTS; break;
                case "FALL": mat = Material.IRON_BOOTS; break;
                case "SMALL_FALL": mat = Material.LEATHER_BOOTS; break;
                case "WARNING": mat = Material.ORANGE_DYE; break;
                case "THROW": mat = Material.SNOWBALL; break;
                case "CHARGE": mat = Material.IRON_AXE; break;
                case "SADDLE": mat = Material.SADDLE; break;
                case "BREATH": mat = Material.TURTLE_HELMET; break;
                case "SWIM":
                case "SPLASH":
                case "HURT_DROWN":
                    mat = Material.WATER_BUCKET; break;
                case "TRADE": mat = Material.EMERALD; break;
                case "JUMP": mat = Material.RABBIT_FOOT; break;
                case "CELEBRATE": mat = Material.FIREWORK_ROCKET; break;
                case "SHOOT": mat = Material.ARROW; break;
                case "HAPPY": mat = Material.COOKED_PORKCHOP; break;
                case "YES": mat = Material.LIME_WOOL; break;
                case "NO": mat = Material.RED_WOOL; break;
                case "RETREAT": mat = Material.SHIELD; break;
                case "FLOP": mat = Material.COD; break;
                case "AMBIENT_LAND": mat = Material.SAND; break;
                case "WORRIED_AMBIENT": mat = Material.LIGHT_BLUE_STAINED_GLASS; break;
                case "AGGRESSIVE_AMBIENT": mat = Material.RED_STAINED_GLASS; break;
                case "BITE":
                case "WHINE":
                    mat = Material.BONE; break;
                case "CANT_BREED": mat = Material.WITHER_ROSE; break;
                case "EXPLODE": mat = Material.TNT; break;
                case "DRINK":
                case "PANT":
                case "BURP":
                    mat = Material.POTION; break;
                case "HATCH":
                case "EGG_CRACK":
                case "EGG":
                case "EGG_BREAK":
                case "DESTROY_EGG":
                case "LAY_EGG":
                case "EGG_HATCH": mat = Material.EGG; break;
                case "SHAMBLE_BABY":
                case "SHAMBLE": mat = Material.SCUTE; break;
                case "DIG": mat = Material.COARSE_DIRT; break;
                case "BREAK_BLOCK": mat = Material.IRON_PICKAXE; break;
                case "GROWL": mat = Material.PORKCHOP; break;
                case "HOWL": mat = Material.WHITE_WOOL; break;
                case "SHAKE": mat = Material.STRING; break;
                case "SPAWN": mat = Material.OBSIDIAN; break;
                case "ATTACK_IRON_DOOR": mat = Material.IRON_DOOR; break;
                case "ATTACK_WOODEN_DOOR":
                case "BREAK_WOODEN_DOOR": mat = Material.OAK_DOOR; break;
                case "INFECT":
                case "CONVERTED_TO_ZOMBIFIED":
                case "CONVERTED":
                case "CONVERTED_TO_ZOMBIE":
                    mat = Material.ZOMBIE_HEAD; break;
                case "CURE": mat = Material.GLISTERING_MELON_SLICE; break;
                case "MILK": mat = Material.MILK_BUCKET; break;
                case "PRIMED": mat = Material.FLINT_AND_STEEL; break;
                case "SHEAR":
                case "CONVERT": mat = Material.SHEARS; break;
                case "ATTACK_KNOCKBACK": mat = Material.STICK; break;
                case "ATTACK_WEAK": mat = Material.WOODEN_SWORD; break;
                case "ATTACK_STRONG": mat = Material.DIAMOND_SWORD; break;
                case "ATTACK_NODAMAGE": mat = Material.IRON_HELMET; break;
                case "ATTACK_CRIT": mat = Material.GOLDEN_SWORD; break;
                case "LEVELUP": mat = Material.EXPERIENCE_BOTTLE; break;
                case "RETRIEVE": mat = Material.FISHING_ROD; break;
                case "BREAK": mat = Material.GUNPOWDER; break;
                case "PLACE": mat = Material.SUGAR_CANE; break;
                case "ADD_ITEM":
                case "REMOVE_ITEM":
                case "ROTATE_ITEM": mat = Material.ITEM_FRAME; break;
                case "REPAIR": mat = Material.IRON_INGOT; break;
                case "FLY": mat = Material.ELYTRA; break;
                case "SPLASH_HIGH_SPEED": mat = Material.OAK_BOAT; break;
                case "HURT_FREEZE": mat = Material.ICE; break;
                case "HURT_ON_FIRE": mat = Material.FIRE_CHARGE; break;
                case "HURT_SWEET_BERRY_BUSH": mat = Material.SWEET_BERRIES; break;
                case "CHEST": mat = Material.CHEST; break;
                case "SNEEZE":
                case "PRE_SNEEZE": mat = Material.SLIME_BALL; break;
                case "BLOW_UP":
                case "BLOW_OUT": mat = Material.PUFFERFISH; break;
                case "BURN": mat = Material.BLAZE_POWDER; break;
            }
        } else if (category == Category.ITEMS) {
            switch(soundExtension.toUpperCase()) {
                case "BREAK": mat = Material.IRON_AXE; break;
                case "BLOCK": mat = Material.SHIELD; break;
                case "EMPTY_WATER":
                case "FILL_WATER": mat = Material.WATER_BUCKET; break;
                case "EMPTY_LAVA":
                case "FILL_LAVA": mat = Material.LAVA_BUCKET; break;
                case "DRINK": mat = Material.POTION; break;
                case "FLYING": mat = Material.ELYTRA; break;
                case "TELEPORT": mat = Material.CHORUS_FRUIT; break;
            }
            if (mat == Material.PAPER) {
                if (ITEM_TEXTURES.containsKey(type.toUpperCase())) return ItemUtils.getFromString(ITEM_TEXTURES.get(type));
                return ItemUtils.getFromString(type.toUpperCase());
            }
        }

        return new ItemStack(mat);
    }

    private void addManualSound(String type, String sound, String category, ItemStack stack) {
        Category cat = Category.valueOf(category.split("\\.")[0].toUpperCase());
        try {
            Sound soundEnum = Sound.valueOf(sound);
            SoundWrapper wrapper = new SoundWrapper(category + "." + type, soundEnum, stack, getFancyName(type));
            SoundWrapperBase parent = SOUND_ENTRIES.get(category);
            if (parent instanceof SoundsContainer) {
                wrapper.addToContainer((SoundsContainer) parent);
                wrapper.register();
            }
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    private void addManualSound(String type, String sound, String category) {
        Category cat = Category.valueOf(category.split("\\.")[0].toUpperCase());
        this.addManualSound(type, sound, category, getSoundItemStack(cat, type, type));
    }

    public void fillSounds() {
        long start = System.currentTimeMillis();

        BLOCKS = Arrays.stream(Material.values()).filter(Material::isBlock)
                .sorted(Comparator.comparingInt(m -> -m.name().length())).collect(Collectors.toList());
        ITEMS = Arrays.stream(Material.values()).filter(m -> !m.isBlock() && m.isItem())
                .sorted(Comparator.comparingInt(m -> -m.name().length())).collect(Collectors.toList());

        fillNonAutoTextures();
        fillAutoTextures();
        fillFixes();
        ready = true;

        long end = System.currentTimeMillis();
        VocalMimicryMenu.INSTANCE.getLogger().info("Took " + (end - start) + "ms to fill sounds");
    }

    private void fillFixes() {
        addManualSound("orb_pickup", "ENTITY_EXPERIENCE_ORB_PICKUP", "mobs.other.experience_bottle", new ItemStack(Material.EXPERIENCE_BOTTLE));
        addManualSound("swim",  "ENTITY_FISH_SWIM", "mobs.passive.cod", new ItemStack(Material.COD));
        addManualSound("swim", "ENTITY_FISH_SWIM", "mobs.passive.salmon", new ItemStack(Material.SALMON));
        addManualSound("swim", "ENTITY_FISH_SWIM", "mobs.passive.pufferfish", new ItemStack(Material.PUFFERFISH));
        addManualSound("swim", "ENTITY_FISH_SWIM", "mobs.passive.tropical_fish", new ItemStack(Material.TROPICAL_FISH));
        addManualSound("raid_horn", "EVENT_RAID_HORN", "mobs.hostile.pillager", new ItemStack(Material.BLACK_BANNER));
        addManualSound("raid_horn", "EVENT_RAID_HORN", "mobs.hostile.ravager", new ItemStack(Material.BLACK_BANNER));
        addManualSound("raid_horn", "EVENT_RAID_HORN", "mobs.hostile.evoker", new ItemStack(Material.BLACK_BANNER));
        addManualSound("raid_horn", "EVENT_RAID_HORN", "mobs.hostile.vindicator", new ItemStack(Material.BLACK_BANNER));
    }

    public String getFancyName(String thingName) {
        return Arrays.stream(thingName.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }

    public enum Category {

        MOBS("Mobs", Material.ZOMBIE_HEAD),
        BLOCKS("Blocks", Material.GRASS_BLOCK),
        ITEMS("Items", Material.DIAMOND_SWORD),
        MUSIC("Music", Material.MUSIC_DISC_CAT),
        OTHER("Other", Material.BREWING_STAND);


        private String name;
        private Material material;

        Category(String name, Material material) {
            this.name = name;
            this.material = material;
        }

        public String getName() {
            return name;
        }

        public Material getMaterial() {
            return material;
        }
    }

    public static SoundBreakdown getInstance() {
        return INSTANCE;
    }
}
