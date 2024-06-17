/*
 *                                                            _...---.._
 *                                                        _.'`       -_ ``.
 *                                                    .-'`                 `.
 *                                                 .-`                     q ;
 *                                              _-`                       __  \
 *                                          .-'`                  . ' .   \ `;/
 *                                      _.-`                    /.      `._`/
 *                              _...--'`                        \_`..._
 *                           .'`                         -         `'--:._
 *                        .-`                           \                  `-.
 *                       .                               `-..__.....----...., `.
 *                      '                   `'''---..-''`'              : :  : :
 *                    .` -                '``                           `'   `'
 *                 .-` .` '             .``
 *             _.-` .-`   '            .
 *         _.-` _.-`    .' '         .`
 * (`''--'' _.-`      .'  '        .'
 *  `'----''        .'  .`       .`
 *                .'  .'     .-'`    _____               _    _
 *              .'   :    .-`       |  __ \             | |  | |
 *              `. .`   ,`          | |__) |__ _  _ __  | |_ | |__    ___  _ __
 *               .'   .'            |  ___// _` || '_ \ | __|| '_ \  / _ \| '__|
 *              '   .`              | |   | (_| || | | || |_ | | | ||  __/| |
 *             '  .`                |_|    \__,_||_| |_| \__||_| |_| \___||_|
 *             `  '.
 *             `.___;
 */
package com.ayanix.panther.utils.bukkit;

import org.bukkit.OfflinePlayer;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 *
 * The class is deprecated because BukkitCurrency now includes a Vault option.
 */
@Deprecated
public interface IBukkitVaultEconomyUtils
{

	/**
	 * @return If Vault is enabled and economy hooked.
	 */
	boolean isEnabled();

	/**
	 * Gets whether or not the player can afford a provided amount.
	 *
	 * @param player The player to check.
	 * @param amount The amount to check.
	 * @return True if player can afford this amount, false if not.
	 */
	default boolean canAfford(OfflinePlayer player, double amount)
	{
		return getBalance(player) >= amount;
	}

	/**
	 * Gets the balance of a player.
	 * <p>
	 * If the player does not exist, a balance of 0 is returned.
	 *
	 * @param player The player to get the balance of.
	 * @return The string version.
	 */
	double getBalance(OfflinePlayer player);

	/**
	 * Adds an amount to the player's balance.
	 *
	 * @param player The player to add to.
	 * @param amount The amount to add.
	 * @return If transaction was successful.
	 */
	boolean deposit(OfflinePlayer player, double amount);

	/**
	 * Removes an amount to the player's balance.
	 *
	 * @param player The player to remove from.
	 * @param amount The amount to remove.
	 * @return If transaction was successful.
	 */
	boolean withdraw(OfflinePlayer player, double amount);

	/**
	 * Set the player's balance to a specified amount.
	 *
	 * @param player The player to modify.
	 * @param amount The new balance.
	 * @return If transaction was successful.
	 */
	boolean set(OfflinePlayer player, double amount);

}
