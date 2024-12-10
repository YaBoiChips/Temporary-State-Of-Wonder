package yaboichips.tsow.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

import static yaboichips.tsow.TSoW.MODID;

public class TSoWItems {

    public static HashMap<Item, ResourceLocation> ITEMS = new HashMap<>();


    public static Item register(Item item, String id) {
        ITEMS.put(item, ResourceLocation.fromNamespaceAndPath(MODID, id));
        return item;
    }
}
