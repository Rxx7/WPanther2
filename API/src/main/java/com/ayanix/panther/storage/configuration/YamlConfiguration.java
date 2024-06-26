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
package com.ayanix.panther.storage.configuration;

import com.google.common.base.Charsets;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 * <p>
 * This file was taken from SpigotMC#BungeeCord
 */
@SuppressWarnings("DM_DEFAULT_ENCODING")
public class YamlConfiguration extends ConfigurationProvider
{

	private final ThreadLocal<Yaml> yaml = ThreadLocal.withInitial(() -> {
		Representer representer = new Representer()
		{
			{
				representers.put(Configuration.class, data -> represent(((Configuration) data).self));
			}
		};

		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

		return new Yaml(new Constructor(), representer, options);
	});

	@Override
	public void save(Configuration config, File file) throws IOException
	{
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8))
		{
			save(config, writer);
		}
	}

	@Override
	public void save(Configuration config, Writer writer)
	{
		yaml.get().dump(config.self, writer);
	}

	@Override
	public Configuration load(File file) throws IOException
	{
		return load(file, null);
	}

	@Override
	public Configuration load(File file, Configuration defaults) throws IOException
	{
		try (FileInputStream is = new FileInputStream(file))
		{
			return load(is, defaults);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Configuration load(Reader reader, Configuration defaults)
	{
		Map<String, Object> map = yaml.get().loadAs(reader, LinkedHashMap.class);
		if (map == null)
		{
			map = new LinkedHashMap<>();
		}
		return new Configuration(map, defaults);
	}

	@Override
	public Configuration load(Reader reader)
	{
		return load(reader, null);
	}

	@Override
	public Configuration load(InputStream is)
	{
		return load(is, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Configuration load(InputStream is, Configuration defaults)
	{
		Map<String, Object> map = yaml.get().loadAs(is, LinkedHashMap.class);
		if (map == null)
		{
			map = new LinkedHashMap<>();
		}
		return new Configuration(map, defaults);
	}

	@Override
	public Configuration load(String string)
	{
		return load(string, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Configuration load(String string, Configuration defaults)
	{
		Map<String, Object> map = yaml.get().loadAs(string, LinkedHashMap.class);
		if (map == null)
		{
			map = new LinkedHashMap<>();
		}
		return new Configuration(map, defaults);
	}

}