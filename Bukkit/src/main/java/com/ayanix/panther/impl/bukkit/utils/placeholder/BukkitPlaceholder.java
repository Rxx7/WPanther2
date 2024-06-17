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
package com.ayanix.panther.impl.bukkit.utils.placeholder;

import com.ayanix.panther.utils.bukkit.placeholder.IBukkitPlaceholder;
import com.ayanix.panther.utils.bukkit.placeholder.IBukkitPlaceholderRunnable;

import java.util.HashMap;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitPlaceholder implements IBukkitPlaceholder
{

	private final String                            name;
	private final IBukkitPlaceholderRunnable        runnable;
	private final HashMap<PlaceholderType, Boolean> registered;
	private       boolean                           playerOnly;

	BukkitPlaceholder(String name, IBukkitPlaceholderRunnable runnable)
	{
		this.name = name;
		this.runnable = runnable;

		this.playerOnly = true;

		this.registered = new HashMap<>();
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public boolean isRegistered(PlaceholderType type)
	{
		return registered.getOrDefault(type, false);
	}

	@Override
	public IBukkitPlaceholderRunnable getRunnable()
	{
		return this.runnable;
	}

	@Override
	public boolean isPlayerOnly()
	{
		return playerOnly;
	}

	@Override
	public void setPlayerOnly(boolean value)
	{
		this.playerOnly = value;
	}

	@Override
	public void unregister()
	{
		if(registered.getOrDefault(PlaceholderType.PLACEHOLDERAPI, false)) {
			setRegistered(PlaceholderType.PLACEHOLDERAPI, false);
		}

		if(registered.getOrDefault(PlaceholderType.MVDW_PLACEHOLDER_API, false)) {
			// it is not possible to unregister MVDW placeholders
			setRegistered(PlaceholderType.MVDW_PLACEHOLDER_API, false);
		}
	}

	@Override
	public void setRegistered(PlaceholderType type, boolean value)
	{
		registered.put(type, true);
	}

}