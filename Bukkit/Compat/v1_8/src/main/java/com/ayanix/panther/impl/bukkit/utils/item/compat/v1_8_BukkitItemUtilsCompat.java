package com.ayanix.panther.impl.bukkit.utils.item.compat;

import com.ayanix.panther.utils.bukkit.item.BukkitItemUtilsCompat;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class v1_8_BukkitItemUtilsCompat extends BukkitItemUtilsCompat
{

	@Override
	public boolean isUnbreakable(ItemMeta itemMeta)
	{
		return itemMeta.spigot().isUnbreakable();
	}

	@Override
	public void setUnbreakable(ItemMeta itemMeta, boolean unbreakable, boolean hidden)
	{
		itemMeta.spigot().setUnbreakable(unbreakable);

		if (hidden)
		{
			itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
	}

	@Override
	public void setSkullOwner(SkullMeta meta, String playerName)
	{
		meta.setOwner(playerName);
	}

	@Override
	public boolean isSkullCached(String playerName)
	{
		return false;
	}

}
