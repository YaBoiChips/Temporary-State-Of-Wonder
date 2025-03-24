package yaboichips.tsow.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

import static yaboichips.tsow.TSoW.MODID;

public class TSoWItems {
    public static HashMap<Item, ResourceLocation> ITEMS = new HashMap<>();

    public static Item TIME_DUST = register(new Item(new Item.Properties()), "time_dust");
    public static Item TIME_CLUMP = register(new Item(new Item.Properties()), "time_clump");
    public static Item TIME = register(new Item(new Item.Properties()), "time");

    public static Item GIANTS_HEART = register(new Item(new Item.Properties()), "giants_heart");
    public static Item KEY = register(new Item(new Item.Properties()), "key");
    public static Item CRUDE_BATTERY = register(new Item(new Item.Properties()), "crude_battery");

    public static Item register(Item item, String id) {
        ITEMS.put(item, ResourceLocation.fromNamespaceAndPath(MODID, id));
        return item;
    }
}