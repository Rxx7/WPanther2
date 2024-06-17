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
package com.ayanix.panther.impl.bukkit.locale;

import com.ayanix.panther.impl.bukkit.compat.BukkitVersion;
import com.ayanix.panther.impl.bukkit.utils.BukkitColourUtils;
import com.ayanix.panther.impl.bukkit.utils.packets.WrapperPlayServerSetSubtitleText;
import com.ayanix.panther.impl.bukkit.utils.packets.WrapperPlayServerSetTitleText;
import com.ayanix.panther.impl.bukkit.utils.packets.WrapperPlayServerSetTitleTimes;
import com.ayanix.panther.impl.bukkit.utils.packets.WrapperPlayServerTitle;
import com.ayanix.panther.locale.Message;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitMessage implements Message
{

	private final String            key;
	private final List<String>      values;
	private final BukkitMessage     original;
	private final Map<String, Long> lastSent;

	/**
	 * @param key    Key associated with message.
	 * @param values Actual messages.
	 */
	public BukkitMessage(final String key, final List<String> values)
	{
		this(key, values, null);
	}

	/**
	 * @param key      Key associated with message.
	 * @param values   Actual messages.
	 * @param original The original unaltered message.
	 */
	public BukkitMessage(final String key, final List<String> values, BukkitMessage original)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		this.key = key;
		this.values = values == null ? new ArrayList<>() : values;
		this.original = original;
		this.lastSent = original == null ? new HashMap<>() : original.lastSent;
	}

	@Override
	public String getKey()
	{
		return this.key;
	}

	@Override
	public BukkitMessage replace(final String key, final String value)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null when replacing");
		}

		final List<String> newValues = new ArrayList<>();

		for (final String message : values)
		{
			newValues.add(message.replace("{" + key + "}", value == null ? "" : value));
		}

		return new BukkitMessage(key, newValues, original == null ? this : original);
	}

	@Override
	public List<String> getList(final boolean formatted)
	{
		if (!formatted)
		{
			return new ArrayList<>(this.values);
		}

		final List<String> newValues = new ArrayList<>();

		for (final String message : values)
		{
			newValues.add(BukkitColourUtils.colourise(message));
		}

		return newValues;
	}

	@Override
	public void broadcast()
	{
		for (final Player player : Bukkit.getOnlinePlayers())
		{
			send(player);
		}

		send(Bukkit.getConsoleSender());
	}

	@Override
	@SuppressWarnings("AvoidInstantiatingObjectsInLoops")
	public void send(final Object sender)
	{
		if (sender == null)
		{
			throw new IllegalArgumentException("Sender/player cannot be null");
		}

		if (!(sender instanceof CommandSender))
		{
			throw new UnsupportedOperationException("Sender must be an implementation of CommandSender");
		}

		final CommandSender cSender = (CommandSender) sender;

		for (String message : getList())
		{
			if (cSender instanceof ConsoleCommandSender)
			{
				message = ChatColor.stripColor(message);
			}

			cSender.sendMessage(message);
		}
	}

	@Override
	public void sendOnce(Object sender, long milliseconds)
	{
		if (sender == null)
		{
			throw new IllegalArgumentException("Sender/player cannot be null");
		}

		if (!(sender instanceof CommandSender))
		{
			throw new UnsupportedOperationException("Sender must be an implementation of CommandSender");
		}

		final CommandSender cSender = (CommandSender) sender;

		long lastSent = this.lastSent.getOrDefault(cSender.getName(), 0L);

		if (lastSent > System.currentTimeMillis() - milliseconds)
		{
			return;
		}

		this.lastSent.put(cSender.getName(), System.currentTimeMillis());

		send(sender);
	}

	@Override
	public void sendTitle(Object sender, int fadeIn, int fadeOut, int stay)
	{
		if (!(sender instanceof Player))
		{
			return;
		}

		if (!isProtocolLibEnabled())
		{
			return;
		}

		List<String> message = getList();

		if (message.isEmpty())
		{
			return;
		}

		sendTitleTiming((Player) sender, fadeIn, fadeOut, stay);

		if(BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_17)) {
			WrapperPlayServerSetTitleText title = new WrapperPlayServerSetTitleText();
			title.setTitle(WrappedChatComponent.fromText(message.get(0)));
			title.sendPacket((Player) sender);
		} else {
			WrapperPlayServerTitle title = new WrapperPlayServerTitle();
			title.setAction(EnumWrappers.TitleAction.TITLE);
			title.setTitle(WrappedChatComponent.fromText(message.get(0)));
			title.sendPacket((Player) sender);
		}

		if (message.size() > 1)
		{
			sendSubtitle(sender, fadeIn, fadeOut, stay, true);
		}
	}

	private boolean isProtocolLibEnabled()
	{
		return Bukkit.getServer().getPluginManager().isPluginEnabled("ProtocolLib");
	}

	private void sendTitleTiming(Player player, int fadeIn, int fadeOut, int stay)
	{
		if(BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_17)) {
			WrapperPlayServerSetTitleTimes title = new WrapperPlayServerSetTitleTimes();
			title.setFadeIn(fadeIn);
			title.setFadeOut(fadeOut);
			title.setStay(stay);
			title.sendPacket(player);
		} else {
			WrapperPlayServerTitle title = new WrapperPlayServerTitle();
			title.setAction(EnumWrappers.TitleAction.TIMES);
			title.setFadeIn(fadeIn);
			title.setFadeOut(fadeOut);
			title.setStay(stay);
			title.sendPacket(player);
		}
	}

	private void sendSubtitle(Object sender, int fadeIn, int fadeOut, int stay, boolean useSecondMessage)
	{
		if (!(sender instanceof Player))
		{
			return;
		}

		if (!isProtocolLibEnabled())
		{
			return;
		}

		List<String> message = getList();

		if (message.isEmpty())
		{
			return;
		}

		sendTitleTiming((Player) sender, fadeIn, fadeOut, stay);

		if(BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_17)) {
			WrapperPlayServerSetSubtitleText title = new WrapperPlayServerSetSubtitleText();
			title.setSubtitle(WrappedChatComponent.fromText(message.get(useSecondMessage ? 1 : 0)));
			title.sendPacket((Player) sender);
		} else {
			WrapperPlayServerTitle title = new WrapperPlayServerTitle();
			title.setAction(EnumWrappers.TitleAction.SUBTITLE);
			title.setTitle(WrappedChatComponent.fromText(message.get(useSecondMessage ? 1 : 0)));
			title.sendPacket((Player) sender);
		}
	}

	@Override
	public void sendSubtitle(Object sender, int fadeIn, int fadeOut, int stay)
	{
		sendSubtitle(sender, fadeIn, fadeOut, stay, false);
	}

}