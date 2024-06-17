package com.ayanix.panther.impl.bukkit.enchantment.compat;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public class v1_13_BukkitGlowEnchantment extends Enchantment
{

	public v1_13_BukkitGlowEnchantment(JavaPlugin plugin)
	{
		super(new NamespacedKey(plugin, "glow"));
	}

	@Override
	public String getName()
	{
		return "glow";
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

	@Override
	public int getStartLevel()
	{
		return 1;
	}

	@Override
	public EnchantmentTarget getItemTarget()
	{
		return EnchantmentTarget.ALL;
	}

	@Override
	public boolean isTreasure()
	{
		return false;
	}

	@Override
	public boolean isCursed()
	{
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment other)
	{
		return false;
	}

	@Override
	public boolean canEnchantItem(ItemStack item)
	{
		return true;
	}

}
