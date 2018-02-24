package com.elytradev.fliptop.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IBlockBase {
    void registerItemModel(Item itemBlock);

    Item createItemBlock();

    Block toBlock();
}