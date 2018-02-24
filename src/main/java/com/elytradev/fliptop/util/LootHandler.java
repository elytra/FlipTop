package com.elytradev.fliptop.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootHandler {

    public LootHandler() {
        LootTableList.register(new ResourceLocation("fliptop", "inject/iron_golem"));
    }

    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent evt) {
        if (evt.getName().equals(LootTableList.ENTITIES_IRON_GOLEM)) {
            // do stuff with evt.getTable()
            LootEntry entry = new LootEntryTable(new ResourceLocation("fliptop","inject/iron_golem"), 1, 0, new LootCondition[0], "golem_entry"); // weight doesn't matter since it's the only entry in the pool. Other params set as you wish.

            LootPool pool = new LootPool(new LootEntry[] {entry}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "golem_pool"); // Other params set as you wish.

            evt.getTable().addPool(pool);

        }
    }
}
